package com.gitee.quiet.scrum.service.impl;

import com.gitee.quiet.scrum.entity.ScrumProject;
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
    
    @Override
    public List<ScrumProject> allProjectByUserId(Long userId) {
        return List.of();
    }
}
