package com.gitee.quite.common.service.base;

import com.gitee.quite.common.validation.group.curd.Create;
import com.gitee.quite.common.validation.group.curd.Update;
import com.gitee.quite.common.validation.group.curd.batch.DeleteBatch;
import com.gitee.quite.common.validation.group.curd.batch.RetrieveBatch;
import com.gitee.quite.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quite.common.validation.group.curd.single.RetrieveSingle;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * Post 请求参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class PostParam<T extends BaseEntity, P> {
    
    private static final String ASCEND = "ascend";
    
    private static final String DESCEND = "descend";
    
    private P params;
    
    private Map<String, List<String>> filter;
    
    private Map<String, String> sorter;
    
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
    
    public P getParams() {
        return params;
    }
    
    public void setParams(P params) {
        this.params = params;
    }
    
    public Map<String, List<String>> getFilter() {
        return filter;
    }
    
    public void setFilter(Map<String, List<String>> filter) {
        this.filter = filter;
    }
    
    public Map<String, String> getSorter() {
        return sorter;
    }
    
    public void setSorter(Map<String, String> sorter) {
        this.sorter = sorter;
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
    
    public Pageable page() {
        List<Sort.Order> orders = Lists.newArrayList();
        if (MapUtils.isNotEmpty(this.getSorter())) {
            for (Map.Entry<String, String> entry : this.getSorter().entrySet()) {
                if (ASCEND.equals(entry.getValue())) {
                    orders.add(Sort.Order.asc(entry.getKey()));
                }
                if (DESCEND.equals(entry.getValue())) {
                    orders.add(Sort.Order.desc(entry.getKey()));
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
