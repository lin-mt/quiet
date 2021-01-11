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

package com.gitee.quiet.system.service;

import com.gitee.quiet.system.entity.QuietDepartmentUser;

import java.util.List;
import java.util.Set;

/**
 * 部门成员信息 service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietDepartmentUserService {
    
    /**
     * 根据部门ID获取该部门下的所有用户
     *
     * @param departmentId 部门ID
     * @return 该部门下的所有用户
     */
    List<QuietDepartmentUser> listAllByDepartmentId(Long departmentId);
    
    /**
     * 根据用户ID删除该用户的部门信息
     *
     * @param userId 用户信息
     */
    void deleteByUserId(Long userId);
    
    /**
     * 为部门批量添加用户
     *
     * @param departmentId 要添加的部门ID
     * @param userIds      部门新增的用户ID集合
     */
    void addUsers(Long departmentId, Set<Long> userIds);
    
    /**
     * 批量删除部门中的某些用户
     *
     * @param departmentId 要删除的用户所在的部门ID
     * @param userIds      要删除的用户ID
     */
    void removeUsers(Long departmentId, Set<Long> userIds);
}
