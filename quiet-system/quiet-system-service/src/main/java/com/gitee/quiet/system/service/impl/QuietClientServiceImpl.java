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

import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietClient;
import com.gitee.quiet.system.repository.QuietClientRepository;
import com.gitee.quiet.system.service.QuietClientService;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import com.querydsl.core.BooleanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 客户端实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@SuppressWarnings("deprecation")
public class QuietClientServiceImpl implements QuietClientService {

  public static final String CACHE_INFO = "quiet:system:client:info";

  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final PasswordEncoder passwordEncoder;

  private final QuietClientRepository clientRepository;

  public QuietClientServiceImpl(
      PasswordEncoder passwordEncoder, QuietClientRepository clientRepository) {
    this.passwordEncoder = passwordEncoder;
    this.clientRepository = clientRepository;
  }

  @Override
  @Cacheable(value = CACHE_INFO, key = "#clientId")
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    QuietClient client = clientRepository.findByClientId(clientId);
    if (client != null) {
      return client;
    }
    logger.error("查询不到客户端信息 ClientId:{}", clientId);
    throw new ClientRegistrationException("客户端信息不存在");
  }

  @Override
  public Page<QuietClient> page(QuietClient params, Pageable page) {
    BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
    return clientRepository.findAll(predicate, page);
  }

  @Override
  public QuietClient save(@Validated(Create.class) QuietClient save) {
    QuietClient exist = clientRepository.findByClientId(save.getClientId());
    if (exist != null) {
      throw new ServiceException("client.clientId.exist", save.getClientId());
    }
    save.setClientSecret(passwordEncoder.encode(save.getClientSecret()));
    return clientRepository.save(save);
  }

  @Override
  @CacheEvict(value = CACHE_INFO, allEntries = true)
  public void deleteClientById(@NotNull Long id) {
    clientRepository
        .findById(id)
        .orElseThrow(() -> new ServiceException("client.id.not.exist", id));
    clientRepository.deleteById(id);
  }

  @Override
  @CacheEvict(value = CACHE_INFO, key = "#result.clientId")
  public QuietClient update(@Validated(Update.class) QuietClient update) {
    QuietClient exist = clientRepository.findByClientId(update.getClientId());
    if (exist != null && !exist.getId().equals(update.getId())) {
      throw new ServiceException("client.clientId.exist", update.getClientId());
    }
    update.setClientSecret(passwordEncoder.encode(update.getClientSecret()));
    return clientRepository.saveAndFlush(update);
  }

  @Override
  @CacheEvict(value = CACHE_INFO, key = "#result.clientId")
  public QuietClient removeClientScope(@NotNull Long id, @NotEmpty String clientScope) {
    QuietClient client =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new ServiceException("client.id.not.exist", id));
    client.removeScope(clientScope);
    return clientRepository.saveAndFlush(client);
  }

  @Override
  @CacheEvict(value = CACHE_INFO, key = "#result.clientId")
  public QuietClient removeClientAuthorizedGrantType(
      @NotNull Long id, @NotEmpty String authorizedGrantType) {
    QuietClient client =
        clientRepository
            .findById(id)
            .orElseThrow(() -> new ServiceException("client.id.not.exist", id));
    client.removeAuthorizedGrantType(authorizedGrantType);
    return clientRepository.saveAndFlush(client);
  }
}
