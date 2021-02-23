package com.gitee.quiet.scrum.controller;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.service.util.SpringSecurityUtils;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.params.ScrumProjectParam;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    
    /**
     * 查询当前登陆人的所有项目
     *
     * @return 项目信息
     */
    @PostMapping("/allMyProjects")
    public Result<List<ScrumProject>> allMyProjects() {
        return Result.success(projectService.allProjectByUserId(SpringSecurityUtils.getCurrentUserId()));
    }
    
    /**
     * 新增项目
     *
     * @param param :save 新增的项目信息
     * @return 新增后的项目信息
     */
    @PostMapping("/save")
    public Result<ScrumProject> save(@RequestBody @Validated(Create.class) ScrumProjectParam param) {
        return Result.createSuccess(projectService.save(param.getSave()));
    }
    
}
