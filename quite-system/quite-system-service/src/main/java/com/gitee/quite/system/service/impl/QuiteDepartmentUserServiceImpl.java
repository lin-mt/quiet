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

package com.gitee.quite.system.service.impl;

import com.gitee.quite.system.entity.QuiteDepartmentUser;
import com.gitee.quite.system.repository.QuiteDepartmentUserRepository;
import com.gitee.quite.system.service.QuiteDepartmentUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门成员信息 service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuiteDepartmentUserServiceImpl implements QuiteDepartmentUserService {
    
    private final QuiteDepartmentUserRepository departmentUserRepository;
    
    public QuiteDepartmentUserServiceImpl(QuiteDepartmentUserRepository departmentUserRepository) {
        this.departmentUserRepository = departmentUserRepository;
    }
    
    @Override
    public List<QuiteDepartmentUser> listAllByDepartmentId(Long departmentId) {
        return departmentUserRepository.findAllByDepartmentId(departmentId);
    }
    
    @Override
    public void deleteByUserId(Long userId) {
        departmentUserRepository.deleteByUserId(userId);
    }
}
