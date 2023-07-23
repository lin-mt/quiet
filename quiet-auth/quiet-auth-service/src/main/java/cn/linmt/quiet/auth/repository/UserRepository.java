package cn.linmt.quiet.auth.repository;

import cn.linmt.quiet.auth.entity.User;
import cn.linmt.quiet.jpa.repository.QuietRepository;
import java.util.Optional;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface UserRepository extends QuietRepository<User, Long> {

  /**
   * 根据用户名查询用户信息
   *
   * @param username 用户名
   * @return 用户信息
   */
  Optional<User> findByUsername(@NonNull String username);
}
