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

package com.gitee.quiet.gateway.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.common.constant.cache.Gateway;
import com.gitee.quiet.gateway.entity.QuietRoute;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 从redis获取路由信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public class RedisRouteDefinitionRepository implements RouteDefinitionRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    private final ObjectMapper objectMapper;

    private volatile List<QuietRoute> quietRouteList;

    public RedisRouteDefinitionRepository(RedisTemplate<String, Object> redisTemplate, ObjectMapper objectMapper) {
        this.redisTemplate = redisTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        Object routes = redisTemplate.opsForValue().get(Gateway.ROUTE_DEFINITION);
        if (routes != null) {
            List<QuietRoute> quietRoutes = objectMapper.convertValue(routes, new TypeReference<>() {
            });
            synchronized (RedisRouteDefinitionRepository.class) {
                quietRouteList = quietRoutes;
                redisTemplate.delete(Gateway.ROUTE_DEFINITION);
            }
        }
        List<RouteDefinition> routeDefinitions = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(quietRouteList)) {
            RouteDefinition routeDefinition;
            for (QuietRoute quietRoute : quietRouteList) {
                routeDefinition = new RouteDefinition();
                routeDefinition.setId(quietRoute.getRouteId());
                routeDefinition.setOrder(quietRoute.getOrder());
                try {
                    routeDefinition.setUri(new URI(quietRoute.getUri()));
                } catch (URISyntaxException e) {
                    throw new IllegalArgumentException(e);
                }
                if (CollectionUtils.isNotEmpty(quietRoute.getPredicates())) {
                    List<PredicateDefinition> predicateDefinitions = new ArrayList<>(quietRoute.getPredicates().size());
                    for (String predicate : quietRoute.getPredicates()) {
                        predicateDefinitions.add(new PredicateDefinition(predicate));
                    }
                    routeDefinition.setPredicates(predicateDefinitions);
                }
                if (CollectionUtils.isNotEmpty(quietRoute.getFilters())) {
                    List<FilterDefinition> filterDefinitions = new ArrayList<>(quietRoute.getFilters().size());
                    for (String filter : quietRoute.getFilters()) {
                        filterDefinitions.add(new FilterDefinition(filter));
                    }
                    routeDefinition.setFilters(filterDefinitions);
                }
                routeDefinitions.add(routeDefinition);
            }
        }
        return Flux.fromIterable(routeDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        throw new UnsupportedOperationException();
    }
}
