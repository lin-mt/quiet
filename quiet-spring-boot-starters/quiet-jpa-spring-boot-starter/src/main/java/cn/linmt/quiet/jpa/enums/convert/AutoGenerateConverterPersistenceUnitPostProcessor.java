package cn.linmt.quiet.jpa.enums.convert;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.util.ClassUtils;

public class AutoGenerateConverterPersistenceUnitPostProcessor
    implements PersistenceUnitPostProcessor {

  @Override
  public void postProcessPersistenceUnitInfo(@NonNull MutablePersistenceUnitInfo pui) {
    Arrays.stream(DatabaseEnumType.values())
        .forEach(
            databaseEnumType -> {
              AttributeConverterAutoGenerator generator =
                  new AttributeConverterAutoGenerator(
                      ClassUtils.getDefaultClassLoader(), databaseEnumType.getValueClass());
              findValueEnumClasses(databaseEnumType.getSuperClass()).stream()
                  .map(generator::generate)
                  .map(Class::getName)
                  .forEach(pui::addManagedClassName);
            });
  }

  private Set<Class<?>> findValueEnumClasses(Class<?> superClass) {
    ClassPathScanningCandidateComponentProvider scanner =
        new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AssignableTypeFilter(superClass));
    Class<?> mainClass = cn.linmt.quiet.common.utils.ClassUtils.getMainClass();
    Set<BeanDefinition> beanDefinitions = new HashSet<>();
    if (mainClass != null) {
      beanDefinitions = new HashSet<>(scanner.findCandidateComponents(mainClass.getPackageName()));
    }
    return beanDefinitions.stream()
        .filter(bd -> bd.getBeanClassName() != null)
        .map(bd -> ClassUtils.resolveClassName(bd.getBeanClassName(), null))
        .collect(Collectors.toUnmodifiableSet());
  }
}
