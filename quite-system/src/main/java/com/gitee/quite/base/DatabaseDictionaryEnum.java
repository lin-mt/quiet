package com.gitee.quite.base;

import com.gitee.dictionary.base.BaseDictionaryEnum;

/**
 * 数据字典枚举基类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DatabaseDictionaryEnum extends BaseDictionaryEnum {
    
    /**
     * 值.
     *
     * @return 前端显示的值
     */
    String getValue();
    
    /**
     * 描述.
     *
     * @return 对数据字典的描述
     */
    String getDescription();
}
