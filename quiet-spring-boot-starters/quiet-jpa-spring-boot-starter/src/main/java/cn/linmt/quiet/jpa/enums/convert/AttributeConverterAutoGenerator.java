package cn.linmt.quiet.jpa.enums.convert;

import static net.bytebuddy.description.annotation.AnnotationDescription.Builder.ofType;
import static net.bytebuddy.description.type.TypeDescription.Generic.Builder.parameterizedType;
import static net.bytebuddy.implementation.FieldAccessor.ofField;
import static net.bytebuddy.matcher.ElementMatchers.isDefaultConstructor;
import static net.bytebuddy.matcher.ElementMatchers.named;

import jakarta.persistence.Converter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;
import org.springframework.lang.NonNull;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class AttributeConverterAutoGenerator {

  /** Auto generation suffix. */
  private static final String AUTO_GENERATION_SUFFIX = "$AttributeConverterGeneratedByByteBuddy";

  private final ClassLoader classLoader;

  private final Class<?> valueClass;

  public AttributeConverterAutoGenerator(ClassLoader classLoader, Class<?> valueClass) {
    this.classLoader = classLoader;
    this.valueClass = valueClass;
  }

  public <T> Class<?> generate(Class<T> clazz) {
    try {
      DynamicType.Builder.MethodDefinition.ReceiverTypeDefinition<?> intercept =
          new ByteBuddy()
              .with(
                  new NamingStrategy.AbstractBase() {
                    @NonNull
                    @Override
                    protected String name(@NonNull TypeDescription superClass) {
                      return clazz.getName() + AUTO_GENERATION_SUFFIX;
                    }
                  })
              .subclass(parameterizedType(AttributeConverter.class, clazz, valueClass).build())
              .annotateType(ofType(Converter.class).define("autoApply", true).build())
              .constructor(isDefaultConstructor())
              .intercept(
                  MethodCall.invoke(Object.class.getDeclaredConstructor())
                      .andThen(ofField("enumType").setsValue(clazz)))
              .defineField("enumType", Class.class, Modifier.PRIVATE | Modifier.FINAL)
              .method(named("convertToDatabaseColumn"))
              .intercept(MethodDelegation.to(AttributeConverter.class))
              .method(named("convertToEntityAttribute"))
              .intercept(MethodDelegation.to(AttributeConverter.class));
      try (DynamicType.Unloaded<?> make = intercept.make()) {
        return make.load(
                this.classLoader, ClassLoadingStrategy.Default.INJECTION.allowExistingTypes())
            .getLoaded();
      }
    } catch (NoSuchMethodException | IOException e) {
      // should never happen
      throw new RuntimeException("Failed to get declared constructor.", e);
    }
  }
}
