/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.scrum.vo;

import com.gitee.quiet.scrum.enums.BuildTool;
import com.gitee.quiet.service.vo.SerialVO;
import com.gitee.quiet.system.entity.QuietTeam;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 项目.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumProjectVO extends SerialVO {

    /**
     * 项目名称
     */
    @NotBlank
    @Length(max = 30)
    private String name;

    /**
     * 项目经理
     */
    @NotNull
    private Long manager;

    /**
     * 项目描述信息
     */
    @Length(max = 100)
    private String description;

    /**
     * 需求前缀
     */
    @Length(max = 6)
    private String demandPrefix;

    /**
     * 任务前缀
     */
    @Length(max = 6)
    private String taskPrefix;

    /**
     * 模板ID
     */
    @NotNull
    private Long templateId;

    /**
     * 构建工具
     */
    private BuildTool buildTool;

    /**
     * 项目开始时间
     */
    private LocalDateTime startTime;

    /**
     * 项目结束时间
     */
    private LocalDateTime endTime;

    /**
     * 负责的团队ID集合
     */
    private Set<Long> teamIds;

    /**
     * 项目经理用户名
     */
    private String managerName;

    /**
     * 模板名称
     */
    private String templateName;

    /**
     * 负责该项目的团队信息
     */
    private List<QuietTeam> teams;

    public void addTeamInfo(QuietTeam quietTeam) {
        if (getTeams() == null) {
            setTeams(new ArrayList<>());
        }
        getTeams().add(quietTeam);
    }
}
