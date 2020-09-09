package com.gitee.quite.service;

import com.gitee.quite.entity.QuitePermission;

/**
 * 权限 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuitePermissionService {
    
    /**
     * 新增或更新权限信息.
     *
     * @param quitePermission 新增或更新的权限信息
     * @return 新增或更新的权限信息
     */
    QuitePermission saveOrUpdate(QuitePermission quitePermission);
    
    /**
     * 删除权限信息.
     *
     * @param deleteId 要删除的权限信息的ID
     * @return true：删除成功
     */
    boolean delete(Long deleteId);
}
