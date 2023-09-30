package cn.linmt.quiet.auth.repository;

import cn.linmt.quiet.auth.entity.Role;
import cn.linmt.quiet.jpa.repository.QuietRepository;
import java.util.Collection;
import java.util.Set;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface RoleRepository extends QuietRepository<Role, Long> {

  Set<Role> findByAuthorityIn(Collection<String> authorities);
}
