package com.gitee.quite.common.service.id;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * ID 生成器配置.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@ConfigurationProperties(prefix = "quite.id-generator")
public class IdGenerator {
    
    private Integer workerId = 0;
    
    private Integer dataCenterId = 0;
    
    public Integer getWorkerId() {
        return workerId;
    }
    
    public void setWorkerId(Integer workerId) {
        this.workerId = workerId == null ? 0 : workerId;
    }
    
    public Integer getDataCenterId() {
        return dataCenterId;
    }
    
    public void setDataCenterId(Integer dataCenterId) {
        this.dataCenterId = dataCenterId == null ? 0 : dataCenterId;
    }
}
