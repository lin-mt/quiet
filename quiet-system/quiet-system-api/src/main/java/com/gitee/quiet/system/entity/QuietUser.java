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
        .notNullEq(getEnabled(), quietUser.enabled)
        .getPredicate();
  }
}
