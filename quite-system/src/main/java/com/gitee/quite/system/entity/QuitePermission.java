package com.gitee.quite.system.entity;

import com.gitee.quite.system.base.BaseEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 权限.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quite_permission")
public class QuitePermission extends BaseEntity {
    
    private String urlPattern;
    
    private String preFilterValue;
    
    private String preFilterFilterTarget;
    
    private String preAuthorizeValue;
    
    private String postFilterValue;
    
    private String postAuthorizeValue;
    
    @Basic
    @Column(name = "url_pattern")
    public String getUrlPattern() {
        return urlPattern;
    }
    
    public void setUrlPattern(String urlPattern) {
        this.urlPattern = urlPattern;
    }
    
    @Basic
    @Column(name = "pre_filter_value")
    public String getPreFilterValue() {
        return preFilterValue;
    }
    
    public void setPreFilterValue(String preFilterValue) {
        this.preFilterValue = preFilterValue;
    }
    
    @Basic
    @Column(name = "pre_filter_filter_target")
    public String getPreFilterFilterTarget() {
        return preFilterFilterTarget;
    }
    
    public void setPreFilterFilterTarget(String preFilterFilterTarget) {
        this.preFilterFilterTarget = preFilterFilterTarget;
    }
    
    @Basic
    @Column(name = "pre_authorize_value")
    public String getPreAuthorizeValue() {
        return preAuthorizeValue;
    }
    
    public void setPreAuthorizeValue(String preAuthorizeValue) {
        this.preAuthorizeValue = preAuthorizeValue;
    }
    
    @Basic
    @Column(name = "post_filter_value")
    public String getPostFilterValue() {
        return postFilterValue;
    }
    
    public void setPostFilterValue(String postFilterValue) {
        this.postFilterValue = postFilterValue;
    }
    
    @Basic
    @Column(name = "post_authorize_value")
    public String getPostAuthorizeValue() {
        return postAuthorizeValue;
    }
    
    public void setPostAuthorizeValue(String postAuthorizeValue) {
        this.postAuthorizeValue = postAuthorizeValue;
    }
    
}
