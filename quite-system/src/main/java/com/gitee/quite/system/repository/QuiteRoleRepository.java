package com.gitee.quite.system.repository;

import com.gitee.quite.system.entity.QuiteRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 查询角色信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuiteRoleRepository extends JpaRepository<QuiteRole, Long> {
    
    /**
     * 根据角色名称获取角色信息.
     *
     * @param roleName 角色名称
     * @return 角色信息
     */
    QuiteRole getByRoleName(String roleName);
}
