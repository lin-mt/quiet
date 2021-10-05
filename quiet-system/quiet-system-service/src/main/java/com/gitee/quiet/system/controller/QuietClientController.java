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
        QuietClient client = clientService.removeClientAuthorizedGrantType(dto.getId(),
                dto.getClientAuthorizedGrantType());
        return Result.success(clientConvert.entity2vo(client));
    }
    
}
