package com.gitee.quiet.scrum.repository;

import com.gitee.quiet.scrum.entity.ScrumProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 项目repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumProjectRepository extends JpaRepository<ScrumProject, Long> {

}
