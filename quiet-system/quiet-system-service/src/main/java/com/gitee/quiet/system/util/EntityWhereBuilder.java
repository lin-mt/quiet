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

package com.gitee.quiet.system.util;

import com.gitee.quiet.common.service.util.Where;
import com.gitee.quiet.system.entity.QuietUser;
import com.querydsl.core.BooleanBuilder;

import static com.gitee.quiet.system.entity.QQuietUser.quietUser;

/**
 * 实体查询条件构造器.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class EntityWhereBuilder {
    
    private EntityWhereBuilder() {
    }
    
    public static void build(QuietUser params, BooleanBuilder builder) {
        Where.NotNullEq(params.getId(), quietUser.id, builder);
        Where.NotBlankContains(params.getUsername(), quietUser.username, builder);
        Where.NotNullEq(params.getGender(), quietUser.gender, builder);
        Where.NotBlankContains(params.getPhoneNumber(), quietUser.phoneNumber, builder);
        Where.NotBlankContains(params.getEmailAddress(), quietUser.emailAddress, builder);
        Where.NotNullEq(params.getAccountExpired(), quietUser.accountExpired, builder);
        Where.NotNullEq(params.getAccountLocked(), quietUser.accountLocked, builder);
        Where.NotNullEq(params.getCredentialsExpired(), quietUser.credentialsExpired, builder);
    }
}
