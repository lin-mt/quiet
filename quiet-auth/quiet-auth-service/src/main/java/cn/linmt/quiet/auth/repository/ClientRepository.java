package cn.linmt.quiet.auth.repository;

import cn.linmt.quiet.auth.entity.Client;
import cn.linmt.quiet.jpa.repository.QuietRepository;
import org.springframework.stereotype.Repository;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ClientRepository extends QuietRepository<Client, Long> {}
