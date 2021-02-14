package com.gitee.quiet.system.params;

import com.gitee.quiet.common.service.base.Param;
import com.gitee.quiet.system.entity.QuietClient;

/**
 * 客户端查询参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietClientParam extends Param<QuietClient, QuietClient> {
    
    /**
     * 客户端授权范围
     */
    private String clientScope;
    
    /**
     * 客户端授权类型
     */
    private String clientAuthorizedGrantType;
    
    public String getClientScope() {
        return clientScope;
    }
    
    public void setClientScope(String clientScope) {
        this.clientScope = clientScope;
    }
    
    public String getClientAuthorizedGrantType() {
        return clientAuthorizedGrantType;
    }
    
    public void setClientAuthorizedGrantType(String clientAuthorizedGrantType) {
        this.clientAuthorizedGrantType = clientAuthorizedGrantType;
    }
}
