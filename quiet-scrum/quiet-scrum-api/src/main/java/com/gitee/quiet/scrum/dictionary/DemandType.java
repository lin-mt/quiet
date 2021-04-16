package com.gitee.quiet.scrum.dictionary;

import com.gitee.quiet.common.service.base.TypeKey;

/**
 * 需求类型.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DemandType extends TypeKey {
    
    /**
     * 功能需求
     */
    DemandType Function = () -> "Function";
    
    /**
     * 优化需求
     */
    DemandType Optimize = () -> "Optimize";
    
    @Override
    default String getType() {
        return "DemandType";
    }
    
}
