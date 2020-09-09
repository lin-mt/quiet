package com.gitee.quite.repository;

import com.gitee.quite.entity.QuiteUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 查询用户信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuiteUserRepository extends JpaRepository<QuiteUser, Long> {
    
    /**
     * 根据用户名获取用户信息.
     *
     * @param username 用户名
     * @return 用户信息
     */
    QuiteUser getByUsername(String username);
    
}
