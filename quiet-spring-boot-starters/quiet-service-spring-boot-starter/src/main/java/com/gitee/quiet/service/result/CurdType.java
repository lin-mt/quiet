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

package com.gitee.quiet.service.result;

import com.gitee.quiet.common.constant.service.MessageSourceCode;

/**
 * Curd 结果类型.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public enum CurdType {

  /** 新增成功. */
  CREATE_SUCCESS(MessageSourceCode.Curd.CREATE_SUCCESS),
  /** 新增失败. */
  CREATE_FAILURE(MessageSourceCode.Curd.CREATE_FAILURE),
  /** 更新成功. */
  UPDATE_SUCCESS(MessageSourceCode.Curd.UPDATE_SUCCESS),
  /** 更新失败. */
  UPDATE_FAILURE(MessageSourceCode.Curd.UPDATE_FAILURE),
  /** 查询成功. */
  READ_SUCCESS(MessageSourceCode.Curd.READ_SUCCESS),
  /** 查询失败. */
  READ_FAILURE(MessageSourceCode.Curd.READ_FAILURE),
  /** 删除成功. */
  DELETE_SUCCESS(MessageSourceCode.Curd.DELETE_SUCCESS),
  /** 删除失败. */
  DELETE_FAILURE(MessageSourceCode.Curd.DELETE_FAILURE),
  ;

  private final String code;

  CurdType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
