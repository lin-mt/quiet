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

import com.gitee.quiet.doc.model.FormParam;
import com.gitee.quiet.doc.model.Header;
import com.gitee.quiet.doc.model.PathParam;
import com.gitee.quiet.doc.model.QueryParam;
import com.gitee.quiet.doc.model.Schema;
import com.gitee.quiet.jpa.entity.base.BaseEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

/**
 * api 信息，包含请求参数、请求头等信息.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_api_info")
public class DocApiInfo extends BaseEntity {

    /**
     * 文档ID
     */
    @NotNull
    @Column(name = "api_id")
    private Long apiId;

    /**
     * 路径参数
     */
    @Type(type = "json")
    @Column(name = "path_param", columnDefinition = "json")
    private List<PathParam> pathParam;

    /**
     * 请求体的 jsonSchema
     */
    @Type(type = "json")
    @Column(name = "req_json_body", columnDefinition = "json")
    private Schema reqJsonBody;

    /**
     * form 参数
     */
    @Type(type = "json")
    @Column(name = "req_form", columnDefinition = "json")
    private List<FormParam> reqForm;

    /**
     * 请求文件
     */
    @Column(name = "req_file")
    private String reqFile;

    /**
     * raw
     */
    @Column(name = "req_raw")
    private String reqRaw;

    /**
     * query 参数
     */
    @Type(type = "json")
    @Column(name = "req_query", columnDefinition = "json")
    private List<QueryParam> reqQuery;

    /**
     * 请求头
     */
    @Type(type = "json")
    @Column(name = "headers", columnDefinition = "json")
    private List<Header> headers;

    /*
     * 响应数据的 jsonSchema
     */
    @Type(type = "json")
    @Column(name = "resp_json_body", columnDefinition = "json")
    private Schema respJsonBody;

    /**
     * 响应信息
     */
    @Column(name = "resp_raw")
    private String respRaw;
}
