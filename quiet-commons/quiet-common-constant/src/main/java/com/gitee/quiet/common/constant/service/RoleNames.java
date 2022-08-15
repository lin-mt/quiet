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

/**
 * 角色名称.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@SuppressWarnings({"AlibabaConstantFieldShouldBeUpperCase", "unused"})
public final class RoleNames {

  public static final String ROLE_PREFIX = "ROLE_";

  // 为了角色名称更加直观，没有采用大写+下划线的命名风格

  public static final String SystemAdmin = "ROLE_SystemAdmin";

  public static final String Admin = "ROLE_Admin";

  public static final String ProductOwner = "ROLE_ProductOwner";

  public static final String ScrumMaster = "ROLE_ScrumMaster";

  private RoleNames() {}
}
