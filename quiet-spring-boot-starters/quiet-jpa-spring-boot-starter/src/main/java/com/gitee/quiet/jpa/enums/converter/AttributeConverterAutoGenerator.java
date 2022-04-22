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

import com.gitee.quiet.jpa.enums.base.AttributeConverterInterceptor;
import java.lang.reflect.Modifier;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.NamingStrategy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodCall;
import net.bytebuddy.implementation.MethodDelegation;

import static net.bytebuddy.description.annotation.AnnotationDescription.Builder.ofType;
import static net.bytebuddy.description.type.TypeDescription.Generic.Builder.parameterizedType;
import static net.bytebuddy.implementation.FieldAccessor.ofField;
import static net.bytebuddy.matcher.ElementMatchers.isDefaultConstructor;
import static net.bytebuddy.matcher.ElementMatchers.named;

public class AttributeConverterAutoGenerator {

    /**
     * Auto generation suffix.
     */
    private static final String AUTO_GENERATION_SUFFIX = "$AttributeConverterGeneratedByByteBuddy";

    private final ClassLoader classLoader;

    private final Class<?> valueClass;

    public AttributeConverterAutoGenerator(ClassLoader classLoader, Class<?> valueClass) {
        this.classLoader = classLoader;
        this.valueClass = valueClass;
    }

    public <T> Class<?> generate(Class<T> clazz) {
        try {
            return new ByteBuddy().with(new NamingStrategy.AbstractBase() {
                    @Override
                    protected String name(TypeDescription superClass) {
                        return clazz.getName() + AUTO_GENERATION_SUFFIX;
                    }
                }).subclass(parameterizedType(AttributeConverter.class, clazz, valueClass).build())
                .annotateType(ofType(Converter.class).define("autoApply", true).build())
                .constructor(isDefaultConstructor()).intercept(
                    MethodCall.invoke(Object.class.getDeclaredConstructor())
                        .andThen(ofField("enumType").setsValue(clazz)))
                .defineField("enumType", Class.class, Modifier.PRIVATE | Modifier.FINAL)
                .method(named("convertToDatabaseColumn"))
                .intercept(MethodDelegation.to(AttributeConverterInterceptor.class))
                .method(named("convertToEntityAttribute"))
                .intercept(MethodDelegation.to(AttributeConverterInterceptor.class)).make()
                .load(this.classLoader, ClassLoadingStrategy.Default.INJECTION.allowExistingTypes()).getLoaded();
        } catch (NoSuchMethodException e) {
            // should never happen
            throw new RuntimeException("Failed to get declared constructor.", e);
        }
    }

}
