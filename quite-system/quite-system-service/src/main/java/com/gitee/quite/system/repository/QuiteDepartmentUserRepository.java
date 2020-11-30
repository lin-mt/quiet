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

package com.gitee.quite.system.repository;

import com.gitee.quite.system.entity.QuiteDepartmentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门成员信息 repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuiteDepartmentUserRepository extends JpaRepository<QuiteDepartmentUser, Long> {
    
    /**
     * 根据部门ID查询用户信息
     *
     * @param departmentId 部门ID
     * @return 该部门下的用户信息
     */
    List<QuiteDepartmentUser> findAllByDepartmentId(Long departmentId);
    
    /**
     * 根据用户查询该用户所属部门
     *
     * @param userId 用户ID
     * @return 用户ID所属部门信息
     */
    QuiteDepartmentUser getByUserId(Long userId);
}
