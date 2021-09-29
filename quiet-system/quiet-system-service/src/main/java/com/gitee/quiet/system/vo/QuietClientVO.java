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

package com.gitee.quiet.system.vo;

import com.gitee.quiet.service.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 客户端 vo
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt<a>
 */
@Getter
@Setter
public class QuietClientVO extends BaseVO {
    
    @NotBlank
    @Length(max = 20)
    private String clientId;
    
    @NotBlank
    @Length(max = 30)
    private String clientName;
    
    @NotBlank
    @Length(max = 60)
    private String clientSecret;
    
    private Set<String> resourceIds;
    
    @NotNull
    private Boolean secretRequired;
    
    @NotNull
    private Boolean scoped;
    
    private Set<String> scope;
    
    private Set<String> authorizedGrantTypes;
    
    private Set<String> registeredRedirectUri;
    
    @Min(0)
    @NotNull
    private Integer accessTokenValiditySeconds;
    
    @Min(0)
    @NotNull
    private Integer refreshTokenValiditySeconds;
    
    @NotNull
    @ColumnDefault("0")
    private Boolean autoApprove;
    
    @Length(max = 100)
    private String remark;
}
