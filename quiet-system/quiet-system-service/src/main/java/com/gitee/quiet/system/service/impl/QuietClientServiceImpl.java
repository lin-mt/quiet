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

import com.gitee.quiet.system.entity.QuietClient;
import com.gitee.quiet.system.repository.QuietClientRepository;
import com.gitee.quiet.system.service.QuietClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

/**
 * 客户端实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietClientServiceImpl implements QuietClientService {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private final QuietClientRepository clientRepository;
    
    public QuietClientServiceImpl(QuietClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    
    /**
     * Load a client by the client id. This method must not return null.
     *
     * @param clientId The client id.
     * @return The client details (never null).
     * @throws ClientRegistrationException If the client account is locked, expired, disabled, or invalid for any other
     *                                     reason.
     */
    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        QuietClient client = clientRepository.findByClientId(clientId);
        if (client != null) {
            return client;
        }
        logger.error("查询不到客户端信息 ClientId:{}", clientId);
        throw new ClientRegistrationException("客户端信息不存在");
    }
    
}
