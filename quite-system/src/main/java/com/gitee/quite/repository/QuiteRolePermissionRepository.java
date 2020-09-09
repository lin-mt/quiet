package com.gitee.quite.repository;

import com.gitee.quite.entity.QuiteRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 查询角色-权限信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuiteRolePermissionRepository extends JpaRepository<QuiteRolePermission, Long> {
    
    /**
     * 根据角色ID 和权限ID 查询权限信息.
     *
     * @param roleId       角色ID
     * @param permissionId 权限ID
     * @return 角色-权限信息
     */
    QuiteRolePermission getByRoleIdAndPermissionId(Long roleId, Long permissionId);
}
