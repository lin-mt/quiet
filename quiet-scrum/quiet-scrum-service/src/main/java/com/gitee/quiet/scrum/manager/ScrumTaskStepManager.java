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

import com.gitee.quiet.scrum.entity.ScrumTaskStep;
import com.gitee.quiet.scrum.repository.ScrumTaskStepRepository;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import com.gitee.quiet.scrum.service.ScrumTaskStepService;
import com.gitee.quiet.scrum.service.ScrumTemplateService;
import com.gitee.quiet.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumTaskStepManager {

  private final ScrumTaskStepService taskStepService;
  private final ScrumTaskStepRepository taskStepRepository;
  private final ScrumTemplateService templateService;
  private final ScrumTaskService taskService;

  /**
   * 批量更新任务步骤信息
   *
   * @param templateId 模板ID
   * @param taskSteps 任务步骤信息
   */
  @SuppressWarnings("DuplicatedCode")
  public List<ScrumTaskStep> saveBatch(Long templateId, List<ScrumTaskStep> taskSteps) {
    Set<Long> existIds =
        taskStepService.list(templateId).stream()
            .map(ScrumTaskStep::getId)
            .collect(Collectors.toSet());
    Set<Long> updateIds =
        taskSteps.stream()
            .map(ScrumTaskStep::getId)
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());
    Set<Long> deleteIds =
        existIds.stream().filter(id -> !updateIds.contains(id)).collect(Collectors.toSet());
    if (CollectionUtils.isNotEmpty(deleteIds)) {
      if (taskService.countByTaskStepIdIn(deleteIds) > 0) {
        throw new ServiceException("taskStep.hasTask.canNotDelete", deleteIds);
      }
      taskStepRepository.deleteAllById(deleteIds);
      if (deleteIds.size() == existIds.size()) {
        templateService.disable(templateId);
      }
    }
    Set<String> allNames = new HashSet<>();
    taskSteps.forEach(
        taskStep -> {
          allNames.add(taskStep.getName());
          taskStep.setTemplateId(templateId);
        });
    if (allNames.size() != taskSteps.size()) {
      throw new ServiceException("taskStep.templateId.name.repeat");
    }
    return taskStepRepository.saveAll(taskSteps);
  }
}
