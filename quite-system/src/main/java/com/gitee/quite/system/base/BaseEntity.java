package com.gitee.quite.system.base;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * 实体类的公共属性.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SnowFlakeIdGenerator")
    @GenericGenerator(name = "SnowFlakeIdGenerator", strategy = "com.gitee.quite.id.SnowFlakeIdGenerator")
    private Long id;
    
    @CreatedBy
    @Column(name = "creator", updatable = false)
    private Long creator;
    
    @CreatedDate
    @Column(name = "gmt_create", updatable = false)
    private LocalDateTime gmtCreate;
    
    @LastModifiedBy
    @Column(name = "updater", updatable = false)
    private Long updater;
    
    @LastModifiedDate
    @Column(name = "gmt_update", insertable = false)
    private LocalDateTime gmtUpdate;
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCreator() {
        return creator;
    }
    
    public void setCreator(Long createBy) {
        this.creator = createBy;
    }
    
    public LocalDateTime getGmtCreate() {
        return gmtCreate;
    }
    
    public void setGmtCreate(LocalDateTime gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
    
    public Long getUpdater() {
        return updater;
    }
    
    public void setUpdater(Long updateBy) {
        this.updater = updateBy;
    }
    
    public LocalDateTime getGmtUpdate() {
        return gmtUpdate;
    }
    
    public void setGmtUpdate(LocalDateTime gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }
    
}
