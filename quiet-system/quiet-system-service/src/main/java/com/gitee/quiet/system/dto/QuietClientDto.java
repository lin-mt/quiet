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

package com.gitee.quiet.system.dto;

import com.gitee.quiet.common.service.base.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * 客户端信息转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietClientDto extends BaseDto {
    
    private String clientId;
    
    private String clientName;
    
    private String clientSecret;
    
    private Set<String> resourceIds;
    
    private Boolean secretRequired;
    
    private Boolean scoped;
    
    private Set<String> scope;
    
    private Set<String> authorizedGrantTypes;
    
    private Set<String> registeredRedirectUri;
    
    private Integer accessTokenValiditySeconds;
    
    private Integer refreshTokenValiditySeconds;
    
    private Boolean autoApprove;
    
    private String remark;
    
    private String clientScope;
    
    private String clientAuthorizedGrantType;
}

