package com.gitee.quiet.common.service.base;

import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import org.apache.commons.lang3.StringUtils;

/**
 * 拥有 type 和 key 层级关系的实体.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface TypeKey {
    
    /**
     * 获取 type
     *
     * @return type
     */
    String getType();
    
    /**
     * 获取 key
     *
     * @return key
     */
    String getKey();
    
    default boolean equals(Dictionary dictionary) {
        if (dictionary == null) {
            return false;
        }
        if (StringUtils.isBlank(dictionary.getType())) {
            return false;
        }
        if (StringUtils.isBlank(dictionary.getKey())) {
            return false;
        }
        return dictionary.getType().equals(getType()) && dictionary.getKey().equals(getKey());
    }
}
