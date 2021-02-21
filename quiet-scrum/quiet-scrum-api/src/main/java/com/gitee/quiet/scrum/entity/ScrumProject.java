package com.gitee.quiet.scrum.entity;

import com.gitee.quiet.common.service.base.BaseEntity;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * 项目.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Entity
@Table(name = "scrum_project")
public class ScrumProject extends BaseEntity {

    @NotNull(message = "{project.name}{not.null}")
    @Length(max = 30, message = "{project.name.length}{{length.max.limit}}")
    @Column(name = "project_name", nullable = false, length = 30)
    private String name;
    
    @NotNull(message = "{project.description}{not.null}")
    @Length(max = 100, message = "{project.description.length}{length.max.limit}")
    @Column(name = "project_description", length = 100)
    private String description;
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}
