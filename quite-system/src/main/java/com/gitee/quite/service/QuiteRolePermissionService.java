package com.gitee.quite.service;

import com.gitee.quite.entity.QuiteRolePermission;

/**
 * 角色-权限 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuiteRolePermissionService {
    
    /**
     * 新增角色-权限信息.
     *
     * @param rolePermission 角色-权限信息
     * @return 新增的角色-权限信息
     */
    QuiteRolePermission saveOrUpdate(QuiteRolePermission rolePermission);
    
    /**
     * 删除角色-权限信息.
     *
     * @param deleteId 要删除的角色-权限信息ID
     * @return true：删除成功
     */
    boolean delete(Long deleteId);
}
