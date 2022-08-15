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

package com.gitee.quiet.system.controller;

import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.system.convert.QuietTeamConvert;
import com.gitee.quiet.system.dto.QuietTeamDTO;
import com.gitee.quiet.system.entity.QuietTeam;
import com.gitee.quiet.system.service.QuietTeamService;
import com.gitee.quiet.system.vo.QuietTeamVO;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 团队 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/team")
public class QuietTeamController {

  private final QuietTeamService teamService;

  private final QuietTeamConvert teamConvert;

  /**
   * 根据团队名称查询团队信息
   *
   * @param dto :teamName 团队名称
   * @return 团队信息
   */
  @GetMapping("/list-teams-by-team-name")
  public Result<List<QuietTeamVO>> listTeamsByTeamName(QuietTeamDTO dto) {
    List<QuietTeam> teams = teamService.listTeamsByTeamName(dto.getTeamName(), 9);
    return Result.success(teamConvert.entities2vos(teams));
  }

  /**
   * 分页查询团队信息.
   *
   * @param dto 查询参数
   * @return 团队信息
   */
  @GetMapping("/page")
  public Result<Page<QuietTeamVO>> page(QuietTeamDTO dto) {
    Page<QuietTeam> teamPage = teamService.page(teamConvert.dto2entity(dto), dto.page());
    return Result.success(teamConvert.page2page(teamPage));
  }

  /**
   * 新增团队.
   *
   * @param dto 新增的团队信息
   * @return 新增后的团队信息
   */
  @PostMapping
  public Result<QuietTeamVO> save(@RequestBody @Validated(Create.class) QuietTeamDTO dto) {
    QuietTeam team = teamService.saveOrUpdate(teamConvert.dto2entity(dto));
    return Result.createSuccess(teamConvert.entity2vo(team));
  }

  /**
   * 删除团队.
   *
   * @param id 删除的团队ID
   * @return Result
   */
  @DeleteMapping("/{id}")
  @PreAuthorize(value = "hasRole('Admin')")
  public Result<Object> delete(@PathVariable Long id) {
    teamService.deleteTeam(id);
    return Result.deleteSuccess();
  }

  /**
   * 更新团队.
   *
   * @param dto 更新的团队信息
   * @return 新增后的团队信息
   */
  @PutMapping
  public Result<QuietTeamVO> update(@RequestBody @Validated(Update.class) QuietTeamDTO dto) {
    QuietTeam team = teamService.saveOrUpdate(teamConvert.dto2entity(dto));
    return Result.updateSuccess(teamConvert.entity2vo(team));
  }
}
