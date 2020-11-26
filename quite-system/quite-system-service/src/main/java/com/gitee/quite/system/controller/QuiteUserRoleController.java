/*
 * Copyright 2020 lin-mt@outlook.com
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

package com.gitee.quite.system.controller;

import com.gitee.quite.common.service.base.Param;
import com.gitee.quite.common.service.result.Result;
import com.gitee.quite.common.validation.group.curd.Create;
import com.gitee.quite.common.validation.group.curd.Update;
import com.gitee.quite.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quite.system.entity.QuiteUserRole;
import com.gitee.quite.system.service.QuiteUserRoleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户-角色 Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/userRole")
public class QuiteUserRoleController {
    
    private final QuiteUserRoleService userRoleService;
    
    public QuiteUserRoleController(QuiteUserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    
    /**
     * 新增用户-角色信息.
     *
     * @param postParam :save 用户-角色对应信息
     * @return 新增结果
     */
    @PostMapping("/save")
    public Result<QuiteUserRole> save(@RequestBody @Validated(Create.class) QuiteUserRoleParam postParam) {
        return Result.success(userRoleService.saveOrUpdate(postParam.getSave()));
    }
    
    /**
     * 更新用户-角色信息.
     *
     * @param postParam :update 用户-角色对应信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public Result<QuiteUserRole> saveOrUpdate(@RequestBody @Validated(Update.class) QuiteUserRoleParam postParam) {
        return Result.updateSuccess(userRoleService.saveOrUpdate(postParam.getUpdate()));
    }
    
    /**
     * 批量删除用户-角色对应关系.
     *
     * @param postParam :deleteIds 要删除的ID集合
     * @return 删除结果
     */
    @DeleteMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuiteUserRoleParam postParam) {
        userRoleService.deleteByIds(postParam.getDeleteIds());
        return Result.deleteSuccess();
    }
    
    static class QuiteUserRoleParam extends Param<QuiteUserRole, QuiteUserRole> {
    
    }
}
