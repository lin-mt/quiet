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

package com.gitee.quiet.common.util;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import org.apache.commons.lang3.StringUtils;

/**
 * String 风格转换工具.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class StringConverterUtil {

    private static final Converter<String, String> UNDERSCORE_TO_CAMEL_CONVERTER = CaseFormat.LOWER_UNDERSCORE.converterTo(
        CaseFormat.LOWER_CAMEL);

    private static final Converter<String, String> CAMEL_TO_UNDERSCORE_CONVERTER = CaseFormat.LOWER_CAMEL.converterTo(
        CaseFormat.LOWER_UNDERSCORE);

    private StringConverterUtil() {
    }

    /**
     * 转为驼峰风格的字符串
     *
     * @param target 要转换的字符串
     * @return 驼峰风格的字符串
     */
    public static String lowerCamel(String target) {
        if (StringUtils.isNotBlank(target)) {
            return UNDERSCORE_TO_CAMEL_CONVERTER.convert(target);
        }
        return target;
    }

    /**
     * 转为下划线风格的字符串
     *
     * @param target 要转换的字符串
     * @return 下划线风格的字符串
     */
    public static String lowerUnderscore(String target) {
        if (StringUtils.isNotBlank(target)) {
            return CAMEL_TO_UNDERSCORE_CONVERTER.convert(target);
        }
        return target;
    }
}
