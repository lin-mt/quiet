/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.common.constant.cache.Gateway;
import com.gitee.quiet.jpa.entity.Dictionary;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietRoute;
import com.gitee.quiet.system.repository.QuietRouteRepository;
import com.gitee.quiet.system.service.QuietRouteService;
import com.querydsl.core.BooleanBuilder;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 路由信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietRouteServiceImpl implements QuietRouteService {

    private final QuietRouteRepository routeRepository;

    private final RedisTemplate<String, Object> redisTemplate;

    public QuietRouteServiceImpl(QuietRouteRepository routeRepository, RedisTemplate<String, Object> redisTemplate) {
        this.routeRepository = routeRepository;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public Page<QuietRoute> page(QuietRoute params, Pageable page) {
        BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
        return routeRepository.findAll(predicate, page);
    }

    @Override
    public QuietRoute save(QuietRoute save) {
        checkInfo(save);
        return routeRepository.save(save);
    }

    @Override
    public void delete(Long id) {
        routeRepository.deleteById(id);
    }

    @Override
    public QuietRoute update(QuietRoute update) {
        checkInfo(update);
        return routeRepository.saveAndFlush(update);
    }

    private void checkInfo(QuietRoute route) {
        if (CollectionUtils.isNotEmpty(route.getPredicates())) {
            route.getPredicates().forEach(predicate -> {
                if (!predicate.contains("=")) {
                    throw new ServiceException("route.predicate.error");
                }
            });
        }
        if (CollectionUtils.isNotEmpty(route.getFilters())) {
            route.getFilters().forEach(filter -> {
                if (!filter.contains("=")) {
                    throw new ServiceException("route.filter.error");
                }
            });
        }
        QuietRoute exist = routeRepository.findByRouteIdAndEnvironment(route.getRouteId(), route.getEnvironment());
        if (exist != null && !exist.getId().equals(route.getId())) {
            throw new ServiceException("route.environment.routeId.exist", route.getEnvironment(), route.getRouteId());
        }
    }

    @Override
    public QuietRoute removePredicate(Long id, String predicate) {
        QuietRoute route = routeRepository.getById(id);
        route.removePredicate(predicate);
        return routeRepository.saveAndFlush(route);
    }

    @Override
    public QuietRoute removeFilter(Long id, String filter) {
        QuietRoute route = routeRepository.getById(id);
        route.removeFilter(filter);
        return routeRepository.saveAndFlush(route);
    }

    @Override
    public void publishRoute(Dictionary<?> environment, Long timeOut) {
        List<QuietRoute> routes = routeRepository.findByEnvironment(environment);
        if (CollectionUtils.isNotEmpty(routes)) {
            redisTemplate.delete(Gateway.ROUTE_DEFINITION);
            if (timeOut == null) {
                redisTemplate.opsForValue().set(Gateway.ROUTE_DEFINITION, routes);
            } else {
                redisTemplate.opsForValue().set(Gateway.ROUTE_DEFINITION, routes, timeOut, TimeUnit.SECONDS);
            }
        } else {
            throw new ServiceException("route.environment.notRouteConfigInfo", environment);
        }
    }
}
