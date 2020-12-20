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

package com.gitee.quiet.system.repository;

import com.gitee.quiet.system.entity.QuietDepartmentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门成员信息 repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietDepartmentUserRepository extends JpaRepository<QuietDepartmentUser, Long> {
    
    /**
     * 根据部门ID查询用户信息
     *
     * @param departmentId 部门ID
     * @return 该部门下的用户信息
     */
    List<QuietDepartmentUser> findAllByDepartmentId(Long departmentId);
    
    /**
     * 根据用户查询该用户所属部门
     *
     * @param userId 用户ID
     * @return 用户ID所属部门信息
     */
    QuietDepartmentUser getByUserId(Long userId);
    
    /**
     * 根据用户ID删除该用户的部门信息
     *
     * @param userId 用户ID
     */
    void deleteByUserId(Long userId);
}
