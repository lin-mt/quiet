package com.gitee.quite.system.service;

import com.gitee.quite.system.entity.QuitePermission;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

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
    
    /**
     * 分页查询权限配置信息
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 查询结果
     */
    QueryResults<QuitePermission> page(QuitePermission params, Pageable page);
}
