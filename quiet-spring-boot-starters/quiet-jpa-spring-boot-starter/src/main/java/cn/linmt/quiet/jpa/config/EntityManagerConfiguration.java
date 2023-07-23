package cn.linmt.quiet.jpa.config;

import cn.linmt.quiet.jpa.enums.convert.AutoGenerateConverterPersistenceUnitPostProcessor;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
public class EntityManagerConfiguration {

  @Bean
  public EntityManagerFactoryBuilderCustomizer entityManagerFactoryBuilderCustomizer() {
    return builder ->
        builder.setPersistenceUnitPostProcessors(
            new AutoGenerateConverterPersistenceUnitPostProcessor());
  }
}
