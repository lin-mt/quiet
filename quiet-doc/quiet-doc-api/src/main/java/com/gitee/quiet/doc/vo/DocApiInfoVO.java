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

package com.gitee.quiet.doc.vo;

import com.gitee.quiet.doc.model.*;
import com.gitee.quiet.service.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * api信息VO.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class DocApiInfoVO extends BaseVO {

  /** 文档ID */
  @NotNull private Long apiId;

  /** 路径参数 */
  private List<PathParam> pathParam;

  /** 请求体的 jsonSchema */
  private Schema reqJsonBody;

  /** form 参数 */
  private List<FormParam> reqForm;

  /** 请求文件 */
  private String reqFile;

  /** raw */
  private String reqRaw;

  /** query 参数 */
  private List<QueryParam> reqQuery;

  /** 请求头 */
  private List<Header> headers;

  /*
   * 响应数据的 jsonSchema
   */
  private Schema respJsonBody;

  /** 响应信息 */
  private String respRaw;
}
