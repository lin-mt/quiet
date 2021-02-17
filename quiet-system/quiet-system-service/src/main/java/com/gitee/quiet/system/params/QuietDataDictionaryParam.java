package com.gitee.quiet.system.params;

import com.gitee.quiet.common.service.base.Param;
import com.gitee.quiet.system.entity.QuietDataDictionary;

/**
 * 数据字典请求参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietDataDictionaryParam extends Param<QuietDataDictionary, QuietDataDictionary> {
    
    /**
     * 数据字典类型
     */
    private String type;
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
}
