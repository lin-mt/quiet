package com.gitee.quiet.common.service.base;

import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@MappedSuperclass
public class DataDictionary extends BaseEntity implements Serializable {
    
    /**
     * 数据字典类型
     */
    @NotNull(groups = {Create.class, Update.class}, message = "{dataDictionary.type}{not.null}")
    @Column(name = "dictionary_type", nullable = false, length = 30)
    @Length(max = 30, message = "{dataDictionary.type.length}{length.max.limit}")
    private String type;
    
    /**
     * 数据字典的key，同数据字典类型下的key不能重复，这个要在业务代码中进行限制
     */
    @Column(name = "dictionary_key", nullable = false, length = 30)
    @Length(max = 30, message = "{dataDictionary.key.length}{length.max.limit}")
    private String key;
    
    /**
     * 数据字典显示的值，前端找不到国际化值的时候使用的默认值
     */
    @NotNull(groups = {Create.class, Update.class}, message = "{dataDictionary.value}{not.null}")
    @Column(name = "dictionary_value", unique = true, nullable = false, length = 30)
    @Length(max = 30, message = "{dataDictionary.value.length}{length.max.limit}")
    private String value;
    
    /**
     * 父级ID
     */
    @Column(name = "parent_id")
    private Long parentId;
    
    @Transient
    private List<DataDictionary> children;
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public Long getParentId() {
        return parentId;
    }
    
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    
    public List<DataDictionary> getChildren() {
        return children;
    }
    
    public void setChildren(List<DataDictionary> children) {
        this.children = children;
    }
    
    public void addChildren(DataDictionary dataDictionary) {
        if (CollectionUtils.isEmpty(getChildren())) {
            setChildren(new ArrayList<>());
        }
        getChildren().add(dataDictionary);
    }
}
