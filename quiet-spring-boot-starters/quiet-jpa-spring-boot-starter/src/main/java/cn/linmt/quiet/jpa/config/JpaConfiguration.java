package cn.linmt.quiet.jpa.config;

import cn.linmt.quiet.jpa.converter.Converter;
import cn.linmt.quiet.jpa.properties.JpaProperties;
import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Configuration
@RequiredArgsConstructor
@EnableJpaAuditing(modifyOnCreate = false)
@EnableConfigurationProperties(JpaProperties.class)
@AutoConfigurationPackage(basePackageClasses = Converter.class)
public class JpaConfiguration {

  @PersistenceContext private EntityManager entityManager;
  @PersistenceUnit private EntityManagerFactory entityManagerFactory;

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
  public CriteriaBuilderFactory createCriteriaBuilderFactory() {
    CriteriaBuilderConfiguration config = Criteria.getDefault();
    // do some configuration
    return config.createCriteriaBuilderFactory(entityManagerFactory);
  }
}
