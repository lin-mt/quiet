/*
 *     Copyright (C) 2022  lin-mt@outlook.com
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.scrum.manager;

import com.gitee.quiet.scrum.entity.ScrumTemplate;
import com.gitee.quiet.scrum.repository.ScrumTemplateRepository;
import com.gitee.quiet.scrum.service.ScrumPriorityService;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.scrum.service.ScrumTaskStepService;
import com.gitee.quiet.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumTemplateManager {

  private final ScrumTemplateRepository templateRepository;
  private final ScrumProjectService projectService;
  private final ScrumTaskStepService taskStepService;
  private final ScrumPriorityService priorityService;

  /**
   * 根据模板ID删除模板信息
   *
   * @param id 模板ID
   */
  public void deleteById(Long id) {
    if (projectService.countByTemplateId(id) > 0) {
      throw new ServiceException("template.hasProjectUse.can.not.delete");
    }
    // 删除任务步骤配置
    taskStepService.deleteByTemplateId(id);
    // 删除优先级配置
    priorityService.deleteByTemplateId(id);
    templateRepository.deleteById(id);
  }

  /**
   * 更新模板状态
   * @param id 模板ID
   * @param enabled 更新的状态
   */
  public void enabled(Long id, Boolean enabled) {
    ScrumTemplate template = templateRepository.findById(id).orElseThrow(() -> new ServiceException("template.id.notExist"));
    boolean newEnabled = BooleanUtils.toBoolean(enabled);
    if (newEnabled) {
      if (template.getId() == null || taskStepService.countByTemplateId(template.getId()) == 0) {
        throw new ServiceException("template.nonTaskStep.canNotEnable", template.getId());
      }
      if (template.getId() == null || priorityService.countByTemplateId(template.getId()) == 0) {
        throw new ServiceException("template.nonPriority.canNotEnable", template.getId());
      }
    }
    template.setEnabled(newEnabled);
    templateRepository.saveAndFlush(template);
  }
}
