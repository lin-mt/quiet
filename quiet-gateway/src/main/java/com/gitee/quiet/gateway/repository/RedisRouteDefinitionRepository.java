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

package com.gitee.quiet.gateway.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.common.constant.cache.Gateway;
import com.gitee.quiet.gateway.entity.QuietRoute;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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
