/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.jpa.config;

import com.gitee.quiet.common.util.ClassUtils;
import com.gitee.quiet.jpa.enums.converter.AutoGenerateConverterPersistenceUnitPostProcessor;
import com.gitee.quiet.jpa.enums.converter.CustomerEnumType;
import com.gitee.quiet.jpa.properties.EnumScanPath;
import com.gitee.quiet.jpa.properties.JpaCustomEnumProperties;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.orm.jpa.EntityManagerFactoryBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
        Optional<List<String>> packageToScan = Optional.ofNullable(jpaCustomEnumProperties.getCustomerEnumPackages());
        List<String> paths = packageToScan.orElseGet(() -> {
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
        Optional<List<CustomerEnumType>> customerEnumTypes = Optional.ofNullable(
            jpaCustomEnumProperties.getCustomerEnumTypes());
        return builder -> builder.setPersistenceUnitPostProcessors(
            new AutoGenerateConverterPersistenceUnitPostProcessor(paths, customerEnumTypes.orElseGet(
                () -> Arrays.stream(CustomerEnumType.values()).collect(Collectors.toList()))));
    }
}
