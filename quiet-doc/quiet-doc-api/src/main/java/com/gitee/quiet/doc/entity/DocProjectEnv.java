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

package com.gitee.quiet.doc.entity;

import com.gitee.quiet.doc.model.Cookie;
import com.gitee.quiet.doc.model.Header;
import com.gitee.quiet.doc.model.HttpProtocol;
import com.gitee.quiet.jpa.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 项目环境配置
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_project_env")
public class DocProjectEnv extends BaseEntity {

  /** 环境名称 */
  @NotEmpty
  @Length(max = 30)
  @Column(name = "env_name", nullable = false, length = 30)
  private String name;

  /** 项目ID */
  @NotNull
  @Column(name = "project_id", nullable = false)
  private Long projectId;

  /** http协议 */
  @NotNull
  @Column(name = "protocol", nullable = false)
  private HttpProtocol protocol;

  /** 请求路径 */
  @Length(max = 90)
  @Column(name = "base_path", length = 90)
  private String basePath;

  /** 请求头 */
  @Type(type = "json")
  @Column(name = "header", columnDefinition = "json")
  private List<Header> headers;

  /** 请求cookie */
  @Type(type = "json")
  @Column(name = "cookie", columnDefinition = "json")
  private List<Cookie> cookies;
}
