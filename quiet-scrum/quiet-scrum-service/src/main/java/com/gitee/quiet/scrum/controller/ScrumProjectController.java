package com.gitee.quiet.scrum.controller;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.service.util.SpringSecurityUtils;
import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/project")
public class ScrumProjectController {
    
    private final ScrumProjectService projectService;
    
    public ScrumProjectController(ScrumProjectService projectService) {
        this.projectService = projectService;
    }
    
    @PostMapping("/allMyProjects")
    public Result<List<ScrumProject>> allMyProjects() {
        return Result.success(projectService.allProjectByUserId(SpringSecurityUtils.getCurrentUserId()));
    }
    
}
