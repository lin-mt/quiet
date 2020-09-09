package com.gitee.quite.service;

import com.gitee.quite.entity.QuiteUserRole;

import java.util.List;

/**
 * 用户-角色 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuiteUserRoleService {
    
    /**
     * 用户新增或更新角色信息.
     *
     * @param quiteUserRole 用户-角色信息
     * @return 用户-角色关联信息
     */
    QuiteUserRole saveOrUpdate(QuiteUserRole quiteUserRole);
    
    /**
     * 批量删除用户的角色信息.
     *
     * @param ids 要删除的id集合
     * @return true：删除成功
     */
    boolean delete(List<Long> ids);
}
