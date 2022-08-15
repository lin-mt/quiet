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
import com.gitee.quiet.system.convert.QuietClientConvert;
import com.gitee.quiet.system.dto.QuietClientDTO;
import com.gitee.quiet.system.entity.QuietClient;
import com.gitee.quiet.system.service.QuietClientService;
import com.gitee.quiet.system.vo.QuietClientVO;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.PageValid;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 客户端Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/client")
@PreAuthorize(value = "hasRole('SystemAdmin')")
public class QuietClientController {

  private final QuietClientService clientService;

  private final QuietClientConvert clientConvert;

  /**
   * 分页查询客户端.
   *
   * @param dto 查询参数
   * @return 查询所有信息
   */
  @GetMapping("/page")
  public Result<Page<QuietClientVO>> page(@Validated(PageValid.class) QuietClientDTO dto) {
    Page<QuietClient> clients = clientService.page(clientConvert.dto2entity(dto), dto.page());
    return Result.success(clientConvert.page2page(clients));
  }

  /**
   * 新增客户端.
   *
   * @param dto 新增的客户端信息
   * @return 新增后的客户端信息
   */
  @PostMapping
  public Result<QuietClientVO> save(@RequestBody @Validated(Create.class) QuietClientDTO dto) {
    QuietClient save = clientService.save(clientConvert.dto2entity(dto));
    return Result.createSuccess(clientConvert.entity2vo(save));
  }

  /**
   * 删除客户端.
   *
   * @param id 删除的客户端ID
   * @return Result
   */
  @DeleteMapping("/{id}")
  public Result<Object> delete(@PathVariable Long id) {
    clientService.deleteClientById(id);
    return Result.deleteSuccess();
  }

  /**
   * 更新客户端.
   *
   * @param dto 更新的客户端信息
   * @return 新增后的客户端信息
   */
  @PutMapping
  public Result<QuietClientVO> update(@RequestBody @Validated(Update.class) QuietClientDTO dto) {
    QuietClient update = clientService.update(clientConvert.dto2entity(dto));
    return Result.updateSuccess(clientConvert.entity2vo(update));
  }

  /**
   * 移除客户端的授权范围
   *
   * @param dto :id 客户端信息ID :clientScope 移除的授权范围
   * @return 更新后的客户端信息
   */
  @PostMapping("/remove-client-scope")
  public Result<QuietClientVO> removeClientScope(@RequestBody QuietClientDTO dto) {
    QuietClient client = clientService.removeClientScope(dto.getId(), dto.getClientScope());
    return Result.success(clientConvert.entity2vo(client));
  }

  /**
   * 移除客户端的认证类型
   *
   * @param dto :id 客户端信息ID :clientAuthorizedGrantType 移除的认证类型
   * @return 更新后的客户端信息
   */
  @PostMapping("/remove-client-authorized-grant-type")
  public Result<QuietClientVO> removeClientAuthorizedGrantType(@RequestBody QuietClientDTO dto) {
    QuietClient client =
        clientService.removeClientAuthorizedGrantType(
            dto.getId(), dto.getClientAuthorizedGrantType());
    return Result.success(clientConvert.entity2vo(client));
  }
}
