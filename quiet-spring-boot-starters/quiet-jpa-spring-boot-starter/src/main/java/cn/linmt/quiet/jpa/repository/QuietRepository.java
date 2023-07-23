package cn.linmt.quiet.jpa.repository;

import cn.linmt.quiet.jpa.entity.QuietEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@NoRepositoryBean
public interface QuietRepository<T extends QuietEntity, ID>
    extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {}
