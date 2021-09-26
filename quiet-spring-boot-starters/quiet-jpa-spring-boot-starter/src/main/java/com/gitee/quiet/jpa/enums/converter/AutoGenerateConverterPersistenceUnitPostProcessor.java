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

public class AutoGenerateConverterPersistenceUnitPostProcessor implements PersistenceUnitPostProcessor {
    
    private final List<String> packagesToScan;
    
    private final List<CustomerEnumType> customerEnumTypes;
    
    public AutoGenerateConverterPersistenceUnitPostProcessor(List<String> packagesToScan,
            List<CustomerEnumType> customerEnumTypes) {
        this.packagesToScan = packagesToScan;
        this.customerEnumTypes = customerEnumTypes;
    }
    
    @Override
    public void postProcessPersistenceUnitInfo(@NonNull MutablePersistenceUnitInfo pui) {
        if (CollectionUtils.isNotEmpty(customerEnumTypes) && CollectionUtils.isNotEmpty(packagesToScan)) {
            customerEnumTypes.forEach(customerEnumType -> {
                AttributeConverterAutoGenerator generator = new AttributeConverterAutoGenerator(
                        ClassUtils.getDefaultClassLoader(), customerEnumType.getValueClass());
                findValueEnumClasses(customerEnumType.getSuperClass()).stream().map(generator::generate)
                        .map(Class::getName).forEach(pui::addManagedClassName);
            });
        }
    }
    
    private Set<Class<?>> findValueEnumClasses(Class<?> superClass) {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AssignableTypeFilter(superClass));
        Set<BeanDefinition> beanDefinitions = new HashSet<>();
        for (String packageToScan : packagesToScan) {
            beanDefinitions.addAll(scanner.findCandidateComponents(packageToScan));
        }
        return beanDefinitions.stream().filter(bd -> bd.getBeanClassName() != null)
                .map(bd -> ClassUtils.resolveClassName(bd.getBeanClassName(), null))
                .collect(Collectors.toUnmodifiableSet());
    }
}
