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

package com.gitee.quiet.scrum.vo;

import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.system.entity.QuietTeam;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 项目详细信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class ScrumProjectDetail {

    /**
     * 项目信息
     */
    private ScrumProject project;

    /**
     * 团队信息
     */
    private List<QuietTeam> teams;

}
