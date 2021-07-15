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

package com.gitee.quiet.system.controller;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.validation.group.Create;
import com.gitee.quiet.common.validation.group.Update;
import com.gitee.quiet.system.convert.QuietClientConvert;
import com.gitee.quiet.system.dto.QuietClientDto;
import com.gitee.quiet.system.entity.QuietClient;
import com.gitee.quiet.system.service.QuietClientService;
import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<QueryResults<QuietClient>> page(QuietClientDto dto) {
        return Result.success(clientService.page(clientConvert.dtoToEntity(dto), dto.page()));
    }
    
    /**
     * 新增客户端.
     *
     * @param dto 新增的客户端信息
     * @return 新增后的客户端信息
     */
    @PostMapping
    public Result<QuietClient> save(@RequestBody @Validated(Create.class) QuietClientDto dto) {
        return Result.createSuccess(clientService.save(clientConvert.dtoToEntity(dto)));
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
    public Result<QuietClient> update(@RequestBody @Validated(Update.class) QuietClientDto dto) {
        return Result.updateSuccess(clientService.update(clientConvert.dtoToEntity(dto)));
    }
    
    /**
     * 移除客户端的授权范围
     *
     * @param dto :id 客户端信息ID :clientScope 移除的授权范围
     * @return 更新后的客户端信息
     */
    @PostMapping("/removeClientScope")
    public Result<QuietClient> removeClientScope(@RequestBody QuietClientDto dto) {
        return Result.success(clientService.removeClientScope(dto.getId(), dto.getClientScope()));
    }
    
    /**
     * 移除客户端的认证类型
     *
     * @param dto :id 客户端信息ID :clientAuthorizedGrantType 移除的认证类型
     * @return 更新后的客户端信息
     */
    @PostMapping("/removeClientAuthorizedGrantType")
    public Result<QuietClient> removeClientAuthorizedGrantType(@RequestBody QuietClientDto dto) {
        return Result.success(
                clientService.removeClientAuthorizedGrantType(dto.getId(), dto.getClientAuthorizedGrantType()));
    }
    
}
