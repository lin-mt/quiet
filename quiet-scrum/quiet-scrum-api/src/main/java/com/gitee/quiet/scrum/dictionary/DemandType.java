package com.gitee.quiet.scrum.dictionary;

import com.gitee.quiet.common.service.jpa.entity.Dictionary;

/**
 * 需求类型.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DemandType extends Dictionary<DemandType> {
    
    private static final String TYPE = DemandType.class.getSimpleName();
    
    private DemandType(String key) {
        super(TYPE, key);
    }
    
    /**
     * 功能需求
     */
    public static final DemandType Function = new DemandType("Function");
    
    /**
     * 优化需求
     */
    public static final DemandType Optimize = new DemandType("Optimize");
    
}
