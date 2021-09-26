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

package com.gitee.quiet.system.entity;

import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.security.entity.QuietUserDetails;
import com.querydsl.core.BooleanBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

import static com.gitee.quiet.system.entity.QQuietUser.quietUser;

/**
 * 用户信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "quiet_user")
public class QuietUser extends QuietUserDetails {
    
    @Override
    public BooleanBuilder booleanBuilder() {
        // @formatter:off
        return SelectBuilder.booleanBuilder()
                .notNullEq(getId(), quietUser.id)
                .notBlankContains(getUsername(), quietUser.username)
                .notBlankContains(getFullName(), quietUser.fullName)
                .notNullEq(getGender(), quietUser.gender)
                .notBlankContains(getPhoneNumber(), quietUser.phoneNumber)
                .notBlankContains(getEmailAddress(), quietUser.emailAddress)
                .notNullEq(getAccountExpired(), quietUser.accountExpired)
                .notNullEq(getAccountLocked(), quietUser.accountLocked)
                .notNullEq(getCredentialsExpired(), quietUser.credentialsExpired)
                .getPredicate();
        // @formatter:on
    }
}
