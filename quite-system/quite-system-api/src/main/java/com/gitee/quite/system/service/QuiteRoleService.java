package com.gitee.quite.system.service;

import com.gitee.quite.system.entity.QuiteRole;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;

/**
 * 角色 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuiteRoleService extends RoleHierarchy {
    
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
    
    /**
     * 查询所有角色信息
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 查询结果
     */
    QueryResults<QuiteRole> page(QuiteRole params, Pageable page);
}
