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
import com.gitee.quiet.system.convert.QuietRouteConvert;
import com.gitee.quiet.system.dto.QuietRouteDTO;
import com.gitee.quiet.system.entity.QuietRoute;
import com.gitee.quiet.system.service.QuietRouteService;
import com.gitee.quiet.system.vo.QuietRouteVO;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 路由信息Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/route")
@PreAuthorize(value = "hasRole('SystemAdmin')")
public class QuietRouteController {

  private final QuietRouteService routeService;

  private final QuietRouteConvert routeConvert;

  /**
   * 分页查询网关路由.
   *
   * @param dto 查询参数
   * @return 查询所有信息
   */
  @GetMapping("/page")
  public Result<Page<QuietRouteVO>> page(QuietRouteDTO dto) {
    Page<QuietRoute> routePage = routeService.page(routeConvert.dto2entity(dto), dto.page());
    return Result.success(routeConvert.page2page(routePage));
  }

  /**
   * 新增网关路由.
   *
   * @param dto 新增的网关路由信息
   * @return 新增的网关路由信息
   */
  @PostMapping
  public Result<QuietRouteVO> save(@RequestBody @Validated(Create.class) QuietRouteDTO dto) {
    QuietRoute save = routeService.save(routeConvert.dto2entity(dto));
    return Result.createSuccess(routeConvert.entity2vo(save));
  }

  /**
   * 删除网关路由.
   *
   * @param id 删除的网关路由ID
   * @return 删除结果
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    routeService.delete(id);
    return Result.deleteSuccess();
  }

  /**
   * 更新网关路由.
   *
   * @param dto 更新的网关路由信息
   * @return 新增后的网关路由信息
   */
  @PutMapping
  public Result<QuietRouteVO> update(@RequestBody @Validated(Update.class) QuietRouteDTO dto) {
    QuietRoute update = routeService.update(routeConvert.dto2entity(dto));
    return Result.updateSuccess(routeConvert.entity2vo(update));
  }

  /**
   * 发布路由配置.
   *
   * @param dto :environment 发布的环境
   * @return 发布结果
   */
  @PostMapping("/publish-route")
  public Result<Object> publishRoute(@RequestBody QuietRouteDTO dto) {
    routeService.publishRoute(dto.getEnvironment(), 100L);
    return Result.success();
  }

  /**
   * 移除路由断言
   *
   * @param dto :id 路由信息ID :routePredicate 要移除的路由断言
   * @return 移除结果
   */
  @PostMapping("/remove-predicate")
  public Result<QuietRouteVO> removePredicate(@RequestBody QuietRouteDTO dto) {
    QuietRoute route = routeService.removePredicate(dto.getId(), dto.getRoutePredicate());
    return Result.success(routeConvert.entity2vo(route));
  }

  /**
   * 移除路由过滤器
   *
   * @param dto :id 路由信息ID :routePredicate 要移除的过滤器
   * @return 移除结果
   */
  @PostMapping("/remove-filter")
  public Result<QuietRouteVO> removeFilter(@RequestBody QuietRouteDTO dto) {
    QuietRoute route = routeService.removeFilter(dto.getId(), dto.getRouteFilter());
    return Result.success(routeConvert.entity2vo(route));
  }
}
