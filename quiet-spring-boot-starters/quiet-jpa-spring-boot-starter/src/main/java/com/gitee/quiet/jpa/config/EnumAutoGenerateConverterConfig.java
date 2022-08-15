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

package com.gitee.quiet.jpa.config;

import com.gitee.quiet.common.util.ClassUtils;
import com.gitee.quiet.jpa.enums.converter.AutoGenerateConverterPersistenceUnitPostProcessor;
import com.gitee.quiet.jpa.enums.converter.CustomerEnumType;
import com.gitee.quiet.jpa.properties.EnumScanPath;
import com.gitee.quiet.jpa.properties.JpaCustomEnumProperties;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@EnableConfigurationProperties(JpaCustomEnumProperties.class)
public class EnumAutoGenerateConverterConfig {

  private final JpaCustomEnumProperties jpaCustomEnumProperties;

  public EnumAutoGenerateConverterConfig(JpaCustomEnumProperties jpaCustomEnumProperties) {
    this.jpaCustomEnumProperties = jpaCustomEnumProperties;
  }

  @Bean
  public EntityManagerFactoryBuilderCustomizer entityManagerFactoryBuilderCustomizer(
      List<EnumScanPath> enumScanPaths) {
    Optional<List<String>> packageToScan =
        Optional.ofNullable(jpaCustomEnumProperties.getCustomerEnumPackages());
    List<String> paths =
        packageToScan.orElseGet(
            () -> {
              Class<?> mainClass = ClassUtils.getMainClass();
              if (mainClass != null) {
                return Lists.newArrayList(mainClass.getPackageName());
              }
              return Lists.newArrayList();
            });
    if (CollectionUtils.isNotEmpty(enumScanPaths)) {
      for (EnumScanPath enumScanPath : enumScanPaths) {
        if (StringUtils.isNotBlank(enumScanPath.getPath())) {
          paths.add(enumScanPath.getPath());
        }
        if (CollectionUtils.isNotEmpty(enumScanPath.getPaths())) {
          for (String path : enumScanPath.getPaths()) {
            if (StringUtils.isNotBlank(path)) {
              paths.add(path);
            }
          }
        }
      }
    }
    Optional<List<CustomerEnumType>> customerEnumTypes =
        Optional.ofNullable(jpaCustomEnumProperties.getCustomerEnumTypes());
    return builder ->
        builder.setPersistenceUnitPostProcessors(
            new AutoGenerateConverterPersistenceUnitPostProcessor(
                paths,
                customerEnumTypes.orElseGet(
                    () -> Arrays.stream(CustomerEnumType.values()).collect(Collectors.toList()))));
  }
}
