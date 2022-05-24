package com.gitee.quiet.service.utils;

import java.beans.FeatureDescriptor;
import java.util.stream.Stream;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class ObjectUtils {

    private ObjectUtils() {
        throw new IllegalCallerException();
    }

    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target);
    }

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
            .map(FeatureDescriptor::getName)
            .filter(propertyName -> {
                try {
                    return wrappedSource.getPropertyValue(propertyName) == null;
                } catch (Exception e) {
                    return false;
                }
            })
            .toArray(String[]::new);
    }
}
