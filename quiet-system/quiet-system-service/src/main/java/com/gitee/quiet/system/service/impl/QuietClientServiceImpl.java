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

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.common.service.enums.Operation;
import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.system.entity.QuietClient;
import com.gitee.quiet.system.repository.QuietClientRepository;
import com.gitee.quiet.system.service.QuietClientService;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static com.gitee.quiet.system.entity.QQuietClient.quietClient;

/**
 * 客户端实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@SuppressWarnings("deprecation")
public class QuietClientServiceImpl implements QuietClientService {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    public static final String CACHE_INFO = "quiet:system:client";
    
    public static final String CACHE_INFO_CLIENT_DETAILS = CACHE_INFO + ":client_details";
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final PasswordEncoder passwordEncoder;
    
    private final QuietClientRepository clientRepository;
    
    public QuietClientServiceImpl(JPAQueryFactory jpaQueryFactory, PasswordEncoder passwordEncoder,
            QuietClientRepository clientRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.passwordEncoder = passwordEncoder;
        this.clientRepository = clientRepository;
    }
    
    @Override
    @Cacheable(value = CACHE_INFO_CLIENT_DETAILS, key = "#clientId")
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        QuietClient client = clientRepository.findByClientId(clientId);
        if (client != null) {
            return client;
        }
        logger.error("查询不到客户端信息 ClientId:{}", clientId);
        throw new ClientRegistrationException("客户端信息不存在");
    }
    
    @Override
    public QueryResults<QuietClient> page(QuietClient params, Pageable page) {
        return SelectBuilder.booleanBuilder(params).from(jpaQueryFactory, quietClient, page);
    }
    
    @Override
    public QuietClient save(@NotNull QuietClient save) {
        QuietClient exist = clientRepository.findByClientId(save.getClientId());
        if (exist != null) {
            throw new ServiceException("client.clientId,exist", save.getClientId());
        }
        save.setClientSecret(passwordEncoder.encode(save.getClientSecret()));
        return clientRepository.save(save);
    }
    
    @Override
    @CacheEvict(value = CACHE_INFO_CLIENT_DETAILS, allEntries = true)
    public void deleteClient(@NotNull Long id) {
        clientRepository.deleteById(id);
    }
    
    @Override
    @CacheEvict(value = CACHE_INFO_CLIENT_DETAILS, key = "#result.clientId")
    public QuietClient update(@NotNull QuietClient update) {
        QuietClient exist = clientRepository.findByClientId(update.getClientId());
        if (exist != null && !exist.getId().equals(update.getId())) {
            throw new ServiceException("client.clientId,exist", update.getClientId());
        }
        update.setClientSecret(passwordEncoder.encode(update.getClientSecret()));
        return clientRepository.saveAndFlush(update);
    }
    
    @Override
    @CacheEvict(value = CACHE_INFO_CLIENT_DETAILS, key = "#result.clientId")
    public QuietClient changeClientScope(@NotNull Long id, @NotEmpty String clientScope, @NotNull Operation operation) {
        QuietClient client = clientRepository.getOne(id);
        switch (operation) {
            case ADD:
                client.addScope(clientScope);
                break;
            case DELETE:
                client.removeScope(clientScope);
                break;
            default:
                throw new IllegalArgumentException(String.format("不支持的操作类型：%s", operation));
        }
        return clientRepository.saveAndFlush(client);
    }
    
    @Override
    @CacheEvict(value = CACHE_INFO_CLIENT_DETAILS, key = "#result.clientId")
    public QuietClient changeClientAuthorizedGrantType(@NotNull Long id, @NotEmpty String authorizedGrantType,
            @NotNull Operation operation) {
        QuietClient client = clientRepository.getOne(id);
        switch (operation) {
            case ADD:
                client.addAuthorizedGrantType(authorizedGrantType);
                break;
            case DELETE:
                client.removeAuthorizedGrantType(authorizedGrantType);
                break;
            default:
                throw new IllegalArgumentException(String.format("不支持的操作类型：%s", operation));
        }
        return clientRepository.saveAndFlush(client);
    }
}
