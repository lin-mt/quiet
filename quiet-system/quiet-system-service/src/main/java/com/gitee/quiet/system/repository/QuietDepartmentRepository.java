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

package com.gitee.quiet.system.repository;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.system.entity.QuietDepartment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietDepartmentRepository extends QuietRepository<QuietDepartment> {
    
    /**
     * 根据部门名称查询部门信息
     *
     * @param departmentName 部门名称
     * @return 部门信息
     */
    QuietDepartment getByDepartmentName(String departmentName);
    
    /**
     * 根据部门 ID 查询子部门信息
     *
     * @param parentId 父级部门ID
     * @return 所有子部门信息
     */
    List<QuietDepartment> findAllByParentId(Long parentId);
}
