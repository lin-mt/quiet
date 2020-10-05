package com.gitee.quite.system.repository;

import com.gitee.quite.system.entity.QuitePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 查询权限信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuitePermissionRepository extends JpaRepository<QuitePermission, Long> {

}
