package com.gitee.quite.service;

import com.gitee.quite.entity.QuiteRole;

/**
 * 角色 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuiteRoleService {
    
    /**
     * 新增角色信息.
     *
     * @param quiteRole 新增的角色信息
     * @return 新增后的角色信息
     */
    QuiteRole save(QuiteRole quiteRole);
    
    /**
     * 更新角色信息.
     *
     * @param quiteRole 要更新的角色信息
     * @return 更新后的角色信息
     */
    QuiteRole update(QuiteRole quiteRole);
    
    /**
     * 删除角色信息.
     *
     * @param deleteId 要删除的角色ID
     * @return true：删除成功
     */
    boolean delete(Long deleteId);
}
