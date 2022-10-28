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

import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.entity.ScrumTask;
import com.gitee.quiet.scrum.entity.ScrumTaskStep;
import com.gitee.quiet.scrum.repository.ScrumTaskRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.scrum.service.ScrumTaskStepService;
import com.gitee.quiet.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumTaskManager {

  private final ScrumTaskRepository taskRepository;
  private final ScrumDemandService demandService;
  private final ScrumProjectService projectService;
  private final ScrumTaskStepService taskStepService;

  /**
   * 保存或更新任务信息
   *
   * @param scrumTask 要保存/更新的任务
   * @return 保存/更新后的任务信息
   */
  public ScrumTask saveOrUpdate(ScrumTask scrumTask) {
    // TODO 校验执行者和参与者信息
    ScrumDemand demand = demandService.getById(scrumTask.getDemandId());
    ScrumTask exist =
        taskRepository.findByDemandIdAndTitle(scrumTask.getDemandId(), scrumTask.getTitle());
    Long taskStepId = scrumTask.getTaskStepId();
    if (exist != null && !exist.getId().equals(scrumTask.getId())) {
      throw new ServiceException(
          "task.demandId.title.exist", scrumTask.getDemandId(), scrumTask.getTitle());
    }
    taskStepService.getById(taskStepId);
    ScrumProject project = projectService.findById(demand.getProjectId());
    List<ScrumTaskStep> taskSteps = taskStepService.list(project.getTemplateId());
    if (taskSteps.get(0).getId().equals(scrumTask.getTaskStepId())) {
      scrumTask.setStartTime(LocalDateTime.now());
      demand.setStartTime(LocalDateTime.now());
    } else if (taskSteps.get(taskSteps.size() - 1).getId().equals(taskStepId)) {
      scrumTask.setEndTime(LocalDateTime.now());
      List<ScrumTask> tasks = taskRepository.findAllByDemandId(scrumTask.getDemandId());
      List<ScrumTask> unfinished =
          tasks.stream()
              .filter(task -> Objects.isNull(task.getEndTime()))
              .filter(task -> !task.getId().equals(scrumTask.getId()))
              .collect(Collectors.toList());
      if (CollectionUtils.isEmpty(unfinished)) {
        demand.setEndTime(LocalDateTime.now());
      }
    } else {
      scrumTask.setEndTime(null);
      demand.setEndTime(null);
    }
    if (CollectionUtils.isNotEmpty(scrumTask.getPreTaskIds())) {
      Set<Long> existIds =
          taskRepository.findAllById(scrumTask.getPreTaskIds()).stream()
              .map(ScrumTask::getId)
              .collect(Collectors.toSet());
      if (CollectionUtils.isEmpty(existIds) || !existIds.containsAll(scrumTask.getPreTaskIds())) {
        scrumTask.getPreTaskIds().removeAll(existIds);
        throw new ServiceException(
            "task.ids.notExist", Arrays.toString(scrumTask.getPreTaskIds().toArray()));
      }
    }
    demandService.saveOrUpdate(demand);
    return taskRepository.saveAndFlush(scrumTask);
  }
}
