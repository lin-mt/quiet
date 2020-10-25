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

package com.gitee.quite.system.service;

import com.gitee.quite.system.entity.QuiteUserRole;

import java.util.List;

/**
 * 用户-角色 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuiteUserRoleService {
    
    /**
     * 用户新增或更新角色信息.
     *
     * @param quiteUserRole 用户-角色信息
     * @return 用户-角色关联信息
     */
    QuiteUserRole saveOrUpdate(QuiteUserRole quiteUserRole);
    
    /**
     * 批量删除用户的角色信息.
     *
     * @param ids 要删除的id集合
     * @return true：删除成功
     */
    boolean delete(List<Long> ids);
}
