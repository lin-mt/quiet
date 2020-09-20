package com.gitee.quite.service;

import com.gitee.quite.entity.QuiteUser;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 用户 Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuiteUserService extends UserDetailsService {
    
    /**
     * 新增用户.
     *
     * @param quiteUser 用户信息
     * @return true：保存成功 false：保存失败
     */
    QuiteUser save(QuiteUser quiteUser);
    
    /**
     * 删除用户.
     *
     * @param deleteId 要删除的用户的ID
     * @return true：删除成功
     */
    boolean delete(Long deleteId);
    
    /**
     * 更新用户信息.
     *
     * @param user 要更新的用户信息
     * @return 更新后的用户信息
     */
    QuiteUser update(QuiteUser user);
    
    /**
     * 根据实体数据查询.
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 查询结果
     */
    QueryResults<QuiteUser> pageByEntity(QuiteUser params, Pageable page);
}
