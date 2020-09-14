package com.gitee.quite.base;

import com.gitee.linmt.exception.ServiceException;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Post 请求参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class PostParam<T extends BaseEntity> {
    
    private T params;
    
    private T save;
    
    private T update;
    
    private T saveOrUpdate;
    
    private Long getId;
    
    private Long deleteId;
    
    private List<Long> getIds;
    
    private List<Long> deleteIds;
    
    /**
     * 第几页
     */
    private Integer current;
    
    /**
     * 分页大小
     */
    private Integer pageSize;
    
    /**
     * 正序排序字段
     */
    private List<String> asc;
    
    /**
     * 倒序排序字段
     */
    private List<String> desc;
    
    public T getParams() {
        return params;
    }
    
    public void setParams(T params) {
        this.params = params;
    }
    
    public T getSave() {
        checkEntity(save);
        if (save.getId() != null) {
            throw new ServiceException("IdShouldBeNull");
        }
        return save;
    }
    
    public void setSave(T save) {
        this.save = save;
    }
    
    public T getUpdate() {
        if (update.getId() == null) {
            throw new ServiceException("IdCanNotBeNull");
        }
        checkEntity(update);
        return update;
    }
    
    public void setUpdate(T update) {
        this.update = update;
    }
    
    public T getSaveOrUpdate() {
        checkEntity(saveOrUpdate);
        return saveOrUpdate;
    }
    
    public void setSaveOrUpdate(T saveOrUpdate) {
        this.saveOrUpdate = saveOrUpdate;
    }
    
    public Long getGetId() {
        if (getId == null) {
            throw new ServiceException("IdCanNotBeNull");
        }
        return getId;
    }
    
    public void setGetId(Long getId) {
        this.getId = getId;
    }
    
    public Long getDeleteId() {
        if (deleteId == null) {
            throw new ServiceException("IdCanNotBeNull");
        }
        return deleteId;
    }
    
    public void setDeleteId(Long deleteId) {
        this.deleteId = deleteId;
    }
    
    public List<Long> getGetIds() {
        return getIds;
    }
    
    public void setGetIds(List<Long> getIds) {
        this.getIds = getIds;
    }
    
    public List<Long> getDeleteIds() {
        return deleteIds;
    }
    
    public void setDeleteIds(List<Long> deleteIds) {
        this.deleteIds = deleteIds;
    }
    
    public Integer getCurrent() {
        if (current == null || current <= 0) {
            current = 0;
        } else {
            current -= 1;
        }
        return current;
    }
    
    public void setCurrent(Integer current) {
        this.current = current;
    }
    
    public Integer getPageSize() {
        if (pageSize == null || pageSize <= 0) {
            pageSize = 20;
        }
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public List<String> getAsc() {
        return asc;
    }
    
    public void setAsc(List<String> asc) {
        this.asc = asc;
    }
    
    public List<String> getDesc() {
        return desc;
    }
    
    public void setDesc(List<String> desc) {
        this.desc = desc;
    }
    
    public Pageable page() {
        List<Sort.Order> orders = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(this.getAsc())) {
            for (String asc : this.getAsc()) {
                if (!StringUtils.hasText(asc)) {
                    orders.add(Sort.Order.asc(asc));
                }
            }
        }
        if (!CollectionUtils.isEmpty(this.getDesc())) {
            for (String desc : this.getDesc()) {
                if (!StringUtils.hasText(desc)) {
                    orders.add(Sort.Order.asc(desc));
                }
            }
        }
        return PageRequest.of(this.getCurrent(), this.getPageSize(), Sort.by(orders));
    }
    
    /**
     * 校验实例属性是否符合业务要求，该方法只能在 get 方法中调用，否则 Jackson 序列化时就会报错，就无法抛出我们指定的异常， 也无法在全局异常中统一处理，同时在序列化时我们无法预知业务是更新还是保存或者其他操作，将该方法放在
     * get 方法中才能做到需要时才校验.
     *
     * @param entity 保存或更新的实例
     */
    private void checkEntity(T entity) {
        if (entity == null) {
            throw new ServiceException("CanNotBeNull", "保存或更新的实体");
        }
        checkProperties(entity);
    }
    
    /**
     * 校验保存或更新的实例属性是否正确.
     *
     * @param entity 保存或更新的实例
     */
    public void checkProperties(T entity) {
    
    }
}
