package com.gitee.quiet.system.entity;

import com.gitee.quiet.common.service.base.DataDictionary;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_data_dictionary")
public class QuietDataDictionary extends DataDictionary {
    
    @Column(name = "remark", length = 100)
    @Length(max = 100, message = "{dataDictionary.remark.length}{length.max.limit}")
    private String remark;
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
