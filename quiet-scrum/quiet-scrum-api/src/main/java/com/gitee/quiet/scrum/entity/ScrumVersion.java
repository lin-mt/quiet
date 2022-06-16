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

package com.gitee.quiet.scrum.entity;

import com.gitee.quiet.common.core.entity.Serial;
import com.gitee.quiet.jpa.entity.ParentAndSerialEntity;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 项目的版本信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "scrum_version")
public class ScrumVersion extends ParentAndSerialEntity<ScrumVersion> {

    /**
     * 版本名称
     */
    @NotBlank
    @Length(max = 10)
    @Column(name = "version_name", nullable = false, length = 10)
    private String name;

    /**
     * 所属项目ID
     */
    @NotNull
    @Column(name = "project_id", nullable = false)
    private Long projectId;

    /**
     * 计划开始日期
     */
    @NotNull
    @Column(name = "plan_start_date", nullable = false)
    private LocalDate planStartDate;

    /**
     * 计划结束日期
     */
    @NotNull
    @Column(name = "plan_end_date", nullable = false)
    private LocalDate planEndDate;

    /**
     * 版本开始时间
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;

    /**
     * 版本结束时间
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /**
     * 版本备注信息
     */
    @NotBlank
    @Length(max = 1500)
    @Column(name = "remark", nullable = false, length = 1500)
    private String remark;

    /**
     * 迭代信息
     */
    @Transient
    private List<ScrumIteration> iterations;

    @Override
    public int compareTo(@Nullable Serial other) {
        int compare = super.compareTo(other);
        if (compare == 0 && other instanceof ScrumVersion) {
            ScrumVersion otherVersion = (ScrumVersion) other;
            compare = planStartDate.compareTo(otherVersion.getPlanStartDate());
            if (compare == 0) {
                compare = getGmtCreate().compareTo(otherVersion.getGmtCreate());
            }
        }
        return compare;
    }
}
