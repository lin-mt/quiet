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

package com.gitee.quiet.common.constant.service;

/** 国际化 MessageSource Code 常量 */
public class MessageSourceCode {

  /** 常用的编码前缀 */
  public static final String COMMON_CODE_PREFIX = "common";

  /** 未知编码前缀 */
  public static final String UNKNOWN_CODE = "common.unknown.code";

  private MessageSourceCode() {}

  public static final class Curd {

    public static final String CREATE_SUCCESS = "common.curd.create.success";

    public static final String CREATE_FAILURE = "common.curd.create.failure";

    public static final String UPDATE_SUCCESS = "common.curd.update.success";

    public static final String UPDATE_FAILURE = "common.curd.update.failure";

    public static final String READ_SUCCESS = "common.curd.read.success";

    public static final String READ_FAILURE = "common.curd.read.failure";

    public static final String DELETE_SUCCESS = "common.curd.delete.success";

    public static final String DELETE_FAILURE = "common.curd.delete.failure";

    private Curd() {}
  }

  public static final class Account {

    public static final String NO_LOGIN = "common.account.no.login";

    public static final String LOGIN_SUCCESS = "common.account.login.success";

    public static final String LOGIN_FAILURE = "common.account.login.failure";

    public static final String LOGOUT_SUCCESS = "common.account.logout.success";

    public static final String NO_PERMISSION = "common.account.no.permission";

    private Account() {}
  }
}
