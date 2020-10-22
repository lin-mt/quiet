package com.gitee.quite.system.repository;

import com.gitee.quite.system.entity.QuiteRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

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
    
    /**
     * 根据角色名称批量查找角色信息
     *
     * @param roleNames 要查找的角色名称集合
     * @return 角色信息
     */
    List<QuiteRole> findByRoleNameIn(Set<String> roleNames);
    
    /**
     * 根据 ID 查询子角色集合数据
     *
     * @param parentIds 父 ID 集合
     * @return 子角色集合信息
     */
    List<QuiteRole> findByParentIdIn(Collection<Long> parentIds);
}
