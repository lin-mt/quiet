package com.gitee.quiet.scrum.service;

import com.gitee.quiet.scrum.entity.ScrumProject;

import java.util.List;

/**
 * 项目Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface ScrumProjectService {
    
    /**
     * 获取用户的所有项目信息
     *
     * @param userId 用户ID
     */
    List<ScrumProject> allProjectByUserId(Long userId);
    
    /**
     * 新增项目
     *
     * @param save 新增的项目信息
     * @return 新增后的项目信息
     */
    ScrumProject save(ScrumProject save);
}
