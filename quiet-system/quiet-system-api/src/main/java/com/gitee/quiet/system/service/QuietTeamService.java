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

package com.gitee.quiet.system.service;

import com.gitee.quiet.system.entity.QuietTeam;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 团队 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietTeamService {

    /**
     * 分页查询团队信息
     *
     * @param params 查询条件
     * @param page   分页条件
     * @return 查询结果
     */
    Page<QuietTeam> page(QuietTeam params, Pageable page);

    /**
     * 新增或更新团队信息
     *
     * @param team 团队信息
     * @return 新增或更新后的团队信息
     */
    QuietTeam saveOrUpdate(QuietTeam team);

    /**
     * 删除团队信息
     *
     * @param deleteId 要删除的团队 ID
     */
    void deleteTeam(Long deleteId);

    /**
     * 根据团队名称传销团队信息
     *
     * @param teamName 团队名称
     * @param limit    要查询的团队数量
     * @return 团队信息
     */
    List<QuietTeam> listTeamsByTeamName(String teamName, int limit);

    /**
     * 根据团队ID批量查询团队信息
     *
     * @param ids 团队ID集合
     * @return 团队信息
     */
    List<QuietTeam> findAllByIds(Set<Long> ids);

    /**
     * 根据团队ID批量查询团队信息，包含团队的成员信息
     *
     * @param ids 团队ID集合
     * @return 团队信息
     */
    List<QuietTeam> findAllByIdsIncludeMembers(Set<Long> ids);
}
