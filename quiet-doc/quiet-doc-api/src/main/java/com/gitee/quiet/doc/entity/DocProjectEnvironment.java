package com.gitee.quiet.doc.entity;

import com.gitee.quiet.doc.model.Cookie;
import com.gitee.quiet.doc.model.Header;
import com.gitee.quiet.doc.model.HttpProtocol;
import com.gitee.quiet.jpa.entity.base.BaseEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.Length;

/**
 * 项目环境配置
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "doc_project_environment")
public class DocProjectEnvironment extends BaseEntity {

    /**
     * 环境名称
     */
    @NotEmpty
    @Length(max = 30)
    @Column(name = "environment_name", nullable = false, length = 30)
    private String name;

    /**
     * 项目ID
     */
    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    /**
     * http协议
     */
    @NotNull
    @Column(name = "protocol", nullable = false)
    private HttpProtocol protocol;

    /**
     * 请求路径
     */
    @Length(max = 90)
    @Column(name = "base_path", length = 90)
    private String basePath;

    /**
     * 请求头
     */
    @Type(type = "json")
    @Column(name = "header", columnDefinition = "json")
    private List<Header> headers;

    /**
     * 请求cookie
     */
    @Type(type = "json")
    @Column(name = "cookie", columnDefinition = "json")
    private List<Cookie> cookies;
}
