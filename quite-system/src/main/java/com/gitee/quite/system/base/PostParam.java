package com.gitee.quite.system.base;

import com.gitee.quite.system.validation.group.curd.base.Create;
import com.gitee.quite.system.validation.group.curd.base.Update;
import com.gitee.quite.system.validation.group.curd.batch.DeleteBatch;
import com.gitee.quite.system.validation.group.curd.batch.RetrieveBatch;
import com.gitee.quite.system.validation.group.curd.single.DeleteSingle;
import com.gitee.quite.system.validation.group.curd.single.RetrieveSingle;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Post 请求参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class PostParam<T extends BaseEntity, P> {
    
    private P params;
    
    @Valid
    @NotNull(groups = Create.class, message = "{save.entity.info}{not.null}")
    private T save;
    
    @Valid
    @NotNull(groups = Update.class, message = "{update.entity.info}{not.null}")
    private T update;
    
    @NotNull(groups = RetrieveSingle.class, message = "id {not.null}")
    private Long getId;
    
    @NotNull(groups = DeleteSingle.class, message = "id {not.null}")
    private Long deleteId;
    
    @NotNull(groups = RetrieveBatch.class, message = "id {not.null}")
    private List<Long> getIds;
    
    @NotNull(groups = DeleteBatch.class, message = "id {not.null}")
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
    
    public P getParams() {
        return params;
    }
    
    public void setParams(P params) {
        this.params = params;
    }
    
    public T getSave() {
        checkProperties(save);
        return save;
    }
    
    public void setSave(T save) {
        this.save = save;
    }
    
    public T getUpdate() {
        checkProperties(update);
        return update;
    }
    
    public void setUpdate(T update) {
        this.update = update;
    }
    
    public Long getGetId() {
        return getId;
    }
    
    public void setGetId(Long getId) {
        this.getId = getId;
    }
    
    public Long getDeleteId() {
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
     * 校验实例属性
     *
     * @param entity 保存或更新的实例
     */
    public void checkProperties(T entity) {
    
    }
}
