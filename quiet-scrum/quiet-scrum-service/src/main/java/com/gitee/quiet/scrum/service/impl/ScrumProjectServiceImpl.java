package com.gitee.quiet.scrum.service.impl;

import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.repository.ScrumProjectRepository;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 项目Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumProjectServiceImpl implements ScrumProjectService {
    
    private final ScrumProjectRepository projectRepository;
    
    public ScrumProjectServiceImpl(ScrumProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    
    @Override
    public List<ScrumProject> allProjectByUserId(Long userId) {
        return List.of();
    }
    
    @Override
    public ScrumProject save(ScrumProject save) {
        // TODO 负责的团队中时候有相同项目名称的项目
        return projectRepository.save(save);
    }
}
