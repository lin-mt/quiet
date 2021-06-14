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

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.system.entity.QuietDepartmentUser;
import com.gitee.quiet.system.repository.QuietDepartmentUserRepository;
import com.gitee.quiet.system.service.QuietDepartmentUserService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 部门成员信息 service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietDepartmentUserServiceImpl implements QuietDepartmentUserService {
    
    private final QuietDepartmentUserRepository departmentUserRepository;
    
    public QuietDepartmentUserServiceImpl(QuietDepartmentUserRepository departmentUserRepository) {
        this.departmentUserRepository = departmentUserRepository;
    }
    
    @Override
    public List<QuietDepartmentUser> listAllByDepartmentId(@NotNull Long departmentId) {
        return departmentUserRepository.findAllByDepartmentId(departmentId);
    }
    
    @Override
    public void deleteByUserId(@NotNull Long userId) {
        departmentUserRepository.deleteByUserId(userId);
    }
    
    @Override
    public void addUsers(@NotNull Long departmentId, @NotNull Set<Long> userIds) {
        List<QuietDepartmentUser> departmentUsers = this.listAllByDepartmentId(departmentId);
        if (CollectionUtils.isNotEmpty(departmentUsers)) {
            Set<Long> existUserIds = departmentUsers.stream().map(QuietDepartmentUser::getUserId)
                    .collect(Collectors.toSet());
            userIds.removeAll(existUserIds);
        }
        if (CollectionUtils.isNotEmpty(userIds)) {
            List<QuietDepartmentUser> newUserInfo = new ArrayList<>(userIds.size());
            for (Long userId : userIds) {
                newUserInfo.add(new QuietDepartmentUser(departmentId, userId));
            }
            departmentUserRepository.saveAll(newUserInfo);
        }
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUsers(@NotNull Long departmentId, @NotNull @NotEmpty Set<Long> userIds) {
        departmentUserRepository.deleteAllByDepartmentIdAndUserIdIsIn(departmentId, userIds);
    }
}
