/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.system.repository;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.system.entity.QuietRole;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

/**
 * 查询角色信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietRoleRepository extends QuietRepository<QuietRole> {

    /**
     * 根据角色名称获取角色信息.
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    QuietRole findByRoleName(String roleName);

    /**
     * 根据角色名称批量查找角色信息
     *
     * @param roleNames 要查找的角色名称集合
     * @return 角色信息
     */
    List<QuietRole> findByRoleNameIn(Set<String> roleNames);

    /**
     * 根据 ID 查询子角色集合数据
     *
     * @param parentIds 父 ID 集合
     * @return 子角色集合信息
     */
    List<QuietRole> findByParentIdIn(Collection<Long> parentIds);
}
