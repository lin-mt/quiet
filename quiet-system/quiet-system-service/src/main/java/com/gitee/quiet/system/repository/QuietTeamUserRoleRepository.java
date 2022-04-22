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
import com.gitee.quiet.system.entity.QuietTeamUserRole;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * 用户团队角色Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietTeamUserRoleRepository extends QuietRepository<QuietTeamUserRole> {

    /**
     * 根据团队-用户ID批量查询团队成员的角色信息
     *
     * @param teamUserIds 团队-用户ID集合
     * @return 团队成员的角色信息
     */
    List<QuietTeamUserRole> findByTeamUserIdIsIn(Collection<? extends Serializable> teamUserIds);

    /**
     * 根据团队-用户ID批量删除用户的团队角色信息
     *
     * @param teamUserIds 团队-用户ID
     */
    void removeAllByTeamUserIdIsIn(Collection<? extends Serializable> teamUserIds);
}
