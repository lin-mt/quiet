/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.common.service.base;

import com.gitee.quiet.common.service.jpa.entity.BaseEntity;
import com.gitee.quiet.common.validation.group.param.IdValid;
import com.gitee.quiet.common.validation.group.param.OffsetLimitValid;
import com.gitee.quiet.common.validation.group.param.ParamsValid;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.batch.CreateBatch;
import com.gitee.quiet.common.validation.group.param.curd.batch.DeleteBatch;
import com.gitee.quiet.common.validation.group.param.curd.batch.ReadBatch;
import com.gitee.quiet.common.validation.group.param.curd.batch.UpdateBatch;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.common.validation.group.param.curd.single.ReadSingle;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.MapUtils;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * 请求参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class Param<T extends BaseEntity, P> {
    
    private static final String ASCEND = "ascend";
    
    private static final String DESCEND = "descend";
    
    /**
     * 参数
     */
    @NotNull(groups = ParamsValid.class)
    private P params;
    
    @NotNull(groups = IdValid.class)
    private Long id;
    
    /**
     * 过滤条件
     */
    private Map<String, List<String>> filter;
    
    /**
     * 排序条件
     */
    private Map<String, String> sorter;
    
    @Valid
    @NotNull(groups = Create.class)
    private T save;
    
    @Valid
    @NotEmpty(groups = CreateBatch.class)
    private List<T> saveBatch;
    
    @Valid
    @NotEmpty(groups = UpdateBatch.class)
    private List<T> updateBatch;
    
    @Valid
    @NotNull(groups = Update.class)
    private T update;
    
    @NotNull(groups = ReadSingle.class)
    private Long getId;
    
    @NotNull(groups = DeleteSingle.class)
    private Long deleteId;
    
    @NotNull(groups = ReadBatch.class)
    private List<Long> getIds;
    
    @NotNull(groups = DeleteBatch.class)
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
     * 跳过几条数据
     */
    @Min(value = 0L, groups = OffsetLimitValid.class)
    @NotNull(groups = OffsetLimitValid.class)
    private Long offset;
    
    /**
     * 查询几条数据
     */
    @Range(min = 1L, max = 30L, groups = OffsetLimitValid.class)
    @NotNull(groups = OffsetLimitValid.class)
    private Long limit;
    
    public P getParams() {
        return params;
    }
    
    public void setParams(P params) {
        this.params = params;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
        return save;
    }
    
    public void setSave(T save) {
        this.save = save;
    }
    
    public List<T> getSaveBatch() {
        return saveBatch;
    }
    
    public void setSaveBatch(List<T> saveBatch) {
        this.saveBatch = saveBatch;
    }
    
    public List<T> getUpdateBatch() {
        return updateBatch;
    }
    
    public void setUpdateBatch(List<T> updateBatch) {
        this.updateBatch = updateBatch;
    }
    
    public T getUpdate() {
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
        if (pageSize == null || pageSize < 0) {
            pageSize = 20;
        }
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
    
    public Long getOffset() {
        return offset;
    }
    
    public void setOffset(Long offset) {
        this.offset = offset;
    }
    
    public Long getLimit() {
        return limit;
    }
    
    public void setLimit(Long limit) {
        this.limit = limit;
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
}
