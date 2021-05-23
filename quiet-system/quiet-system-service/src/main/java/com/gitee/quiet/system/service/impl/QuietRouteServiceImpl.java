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

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.common.service.enums.Operation;
import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.system.entity.QuietRoute;
import com.gitee.quiet.system.repository.QuietRouteRepository;
import com.gitee.quiet.system.service.QuietRouteService;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.gitee.quiet.system.entity.QQuietRoute.quietRoute;

/**
 * 路由信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietRouteServiceImpl implements QuietRouteService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuietRouteRepository routeRepository;
    
    public QuietRouteServiceImpl(JPAQueryFactory jpaQueryFactory, QuietRouteRepository routeRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.routeRepository = routeRepository;
    }
    
    @Override
    public QueryResults<QuietRoute> page(QuietRoute params, Pageable page) {
        return SelectBuilder.booleanBuilder(params).from(jpaQueryFactory, quietRoute, page);
    }
    
    @Override
    public QuietRoute save(QuietRoute save) {
        QuietRoute exist = routeRepository.findByRouteIdAndEnvironment(save.getRouteId(), save.getEnvironment());
        if (exist != null) {
            throw new ServiceException("route.environment.routeId.exist", save.getEnvironment(), save.getRouteId());
        }
        return routeRepository.save(save);
    }
    
    @Override
    public void delete(Long id) {
        routeRepository.deleteById(id);
    }
    
    @Override
    public QuietRoute update(QuietRoute update) {
        QuietRoute exist = routeRepository.findByRouteIdAndEnvironment(update.getRouteId(), update.getEnvironment());
        if (exist != null && !exist.getId().equals(update.getId())) {
            throw new ServiceException("route.environment.routeId.exist", update.getEnvironment(), update.getRouteId());
        }
        return routeRepository.saveAndFlush(update);
    }
    
    @Override
    public QuietRoute changePredicate(Long id, String predicate, Operation operation) {
        QuietRoute route = routeRepository.getOne(id);
        switch (operation) {
            case ADD:
                route.addPredicate(predicate);
                break;
            case DELETE:
                route.removePredicate(predicate);
                break;
            default:
                throw new IllegalArgumentException(String.format("不支持的操作类型：%s", operation));
        }
        return routeRepository.saveAndFlush(route);
    }
    
    @Override
    public QuietRoute changeFilter(Long id, String filter, Operation operation) {
        QuietRoute route = routeRepository.getOne(id);
        switch (operation) {
            case ADD:
                route.addFilter(filter);
                break;
            case DELETE:
                route.removeFilter(filter);
                break;
            default:
                throw new IllegalArgumentException(String.format("不支持的操作类型：%s", operation));
        }
        return routeRepository.saveAndFlush(route);
    }
}
