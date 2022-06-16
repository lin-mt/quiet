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

import com.gitee.quiet.scrum.entity.ScrumTemplate;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有的模板信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
public class AllTemplate {

    /**
     * 登陆人创建的模板
     */
    private final List<ScrumTemplate> templateCreated = new ArrayList<>();

    /**
     * 非登陆人创建的可选的模板
     */
    private final List<ScrumTemplate> templateSelectable = new ArrayList<>();

}
