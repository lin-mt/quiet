/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.jpa.enums.converter;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.lang.NonNull;
import org.springframework.orm.jpa.persistenceunit.MutablePersistenceUnitInfo;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.util.ClassUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AutoGenerateConverterPersistenceUnitPostProcessor
    implements PersistenceUnitPostProcessor {

  private final List<String> packagesToScan;

  private final List<CustomerEnumType> customerEnumTypes;

  public AutoGenerateConverterPersistenceUnitPostProcessor(
      List<String> packagesToScan, List<CustomerEnumType> customerEnumTypes) {
    this.packagesToScan = packagesToScan;
    this.customerEnumTypes = customerEnumTypes;
  }

  @Override
  public void postProcessPersistenceUnitInfo(@NonNull MutablePersistenceUnitInfo pui) {
    if (CollectionUtils.isNotEmpty(customerEnumTypes)
        && CollectionUtils.isNotEmpty(packagesToScan)) {
      customerEnumTypes.forEach(
          customerEnumType -> {
            AttributeConverterAutoGenerator generator =
                new AttributeConverterAutoGenerator(
                    ClassUtils.getDefaultClassLoader(), customerEnumType.getValueClass());
            findValueEnumClasses(customerEnumType.getSuperClass()).stream()
                .map(generator::generate)
                .map(Class::getName)
                .forEach(pui::addManagedClassName);
          });
    }
  }

  private Set<Class<?>> findValueEnumClasses(Class<?> superClass) {
    ClassPathScanningCandidateComponentProvider scanner =
        new ClassPathScanningCandidateComponentProvider(false);
    scanner.addIncludeFilter(new AssignableTypeFilter(superClass));
    Set<BeanDefinition> beanDefinitions = new HashSet<>();
    for (String packageToScan : packagesToScan) {
      beanDefinitions.addAll(scanner.findCandidateComponents(packageToScan));
    }
    return beanDefinitions.stream()
        .filter(bd -> bd.getBeanClassName() != null)
        .map(bd -> ClassUtils.resolveClassName(bd.getBeanClassName(), null))
        .collect(Collectors.toUnmodifiableSet());
  }
}
