/*
 * Copyright $.today.year lin-mt@outlook.com
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

package com.gitee.quiet.system.entity;

import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.common.service.jpa.entity.BaseEntity;
import com.gitee.quiet.common.service.jpa.entity.Dictionary;
import com.querydsl.core.BooleanBuilder;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

import static com.gitee.quiet.system.entity.QQuietRoute.quietRoute;

/**
 * 路由信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_route")
public class QuietRoute extends BaseEntity {
    
    /**
     * 网关的路由ID
     */
    @NotBlank
    @Length(max = 60)
    @Column(name = "route_id", length = 60, nullable = false)
    private String routeId;
    
    /**
     * 环境，用于批量修改发布
     */
    @NotNull
    @Column(name = "environment", length = 30, nullable = false)
    private Dictionary<?> environment;
    
    /**
     * 路由目标
     */
    @NotBlank
    @Length(max = 200)
    @Column(name = "uri", length = 200, nullable = false)
    private String uri;
    
    /**
     * 排序
     */
    @Column(name = "route_order", nullable = false)
    private int order;
    
    /**
     * 匹配规则
     */
    @Column(name = "predicates", length = 1000)
    private Set<String> predicates;
    
    /**
     * 过滤
     */
    @Column(name = "filters", length = 1000)
    private Set<String> filters;
    
    @Length(max = 300)
    @Column(name = "remark", length = 300)
    private String remark;
    
    public Dictionary<?> getEnvironment() {
        return environment;
    }
    
    public void setEnvironment(Dictionary<?> environment) {
        this.environment = environment;
    }
    
    public String getRouteId() {
        return routeId;
    }
    
    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }
    
    public String getUri() {
        return uri;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public int getOrder() {
        return order;
    }
    
    public void setOrder(int order) {
        this.order = order;
    }
    
    public Set<String> getPredicates() {
        return predicates;
    }
    
    public void setPredicates(Set<String> predicates) {
        this.predicates = predicates;
    }
    
    public Set<String> getFilters() {
        return filters;
    }
    
    public void setFilters(Set<String> filters) {
        this.filters = filters;
    }
    
    public String getRemark() {
        return remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Nullable
    @Override
    public BooleanBuilder booleanBuilder() {
        // @formatter:off
        return SelectBuilder.booleanBuilder()
                .notBlankContains(getRouteId(), quietRoute.routeId)
                .notNullEq(getEnvironment(), quietRoute.environment)
                .notBlankContains(getUri(), quietRoute.uri)
                .notBlankContains(getRemark(), quietRoute.remark)
                .getPredicate();
        // @formatter:on
    }
    
    public void addPredicate(String predicate) {
        if (getPredicates() == null) {
            setPredicates(new HashSet<>());
        }
        getPredicates().add(predicate);
    }
    
    public void removePredicate(String predicate) {
        if (getPredicates() != null) {
            getPredicates().removeAll(predicates);
        }
    }
    
    public void addFilter(String filter) {
        if (getFilters() == null) {
            setFilters(new HashSet<>());
        }
        getFilters().add(filter);
    }
    
    public void removeFilter(String filter) {
        if (getFilters() != null) {
            getFilters().remove(filter);
        }
    }
}
