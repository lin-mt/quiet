package com.gitee.quite.repository;

import com.gitee.quite.entity.QuiteUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 查询用户-角色信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuiteUserRoleRepository extends JpaRepository<QuiteUserRole, Long> {
    
    /**
     * 根据用户ID查询用户ID跟角色的对应关系.
     *
     * @param userId 用户ID
     * @return 用户-角色信息
     */
    List<QuiteUserRole> findByUserId(Long userId);
    
    /**
     * 根据用户ID和角色ID查询是否该用户拥有该角色.
     *
     * @param userId 用户ID
     * @param roleId 角色ID
     * @return 用户-角色对应信息
     */
    Optional<QuiteUserRole> findByUserIdAndRoleId(Long userId, Long roleId);
    
    /**
     * 根据ID批量删除用户-角色信息.
     *
     * @param ids 要删除的ID集合
     * @return true：删除成功
     */
    boolean deleteByIdIn(List<Long> ids);
}
