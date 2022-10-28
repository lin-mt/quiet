/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.scrum.service.impl;

import com.gitee.quiet.jpa.utils.SelectBooleanBuilder;
import com.gitee.quiet.scrum.entity.ScrumProject;
import com.gitee.quiet.scrum.entity.ScrumTask;
import com.gitee.quiet.scrum.entity.ScrumTaskStep;
import com.gitee.quiet.scrum.entity.ScrumTemplate;
import com.gitee.quiet.scrum.repository.ScrumTaskRepository;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import com.gitee.quiet.scrum.service.ScrumTemplateService;
import com.gitee.quiet.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.gitee.quiet.scrum.entity.QScrumTask.scrumTask;

/**
 * 任务信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumTaskServiceImpl implements ScrumTaskService {

  private final JPAQueryFactory jpaQueryFactory;
  private final ScrumTaskRepository taskRepository;
  private final ScrumProjectService projectService;
  private final ScrumTemplateService templateService;

  public ScrumTaskServiceImpl(
          JPAQueryFactory jpaQueryFactory,
      ScrumTaskRepository taskRepository,
      @Lazy ScrumProjectService projectService,
      @Lazy ScrumTemplateService templateService) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.taskRepository = taskRepository;
    this.projectService = projectService;
    this.templateService = templateService;
  }

  @Override
  public List<ScrumTask> list(Set<Long> demandIds, Set<Long> executorIds) {
    if (CollectionUtils.isEmpty(demandIds)) {
      return List.of();
    }
    BooleanBuilder predicate = SelectBooleanBuilder.booleanBuilder().notEmptyIn(demandIds, scrumTask.demandId)
            .notEmptyIn(executorIds, scrumTask.executorId).getPredicate();
    return jpaQueryFactory.selectFrom(scrumTask).where(predicate).fetch();
  }

  @Override
  public void deleteAllByDemandIds(Set<Long> demandIds) {
    if (CollectionUtils.isNotEmpty(demandIds)) {
      taskRepository.deleteAllByDemandIdIn(demandIds);
    }
  }

  @Override
  public List<ScrumTask> findAllByTaskStepId(Long taskStepId) {
    return taskRepository.findAllByTaskStepId(taskStepId);
  }

  @Override
  public void deleteById(Long id) {
    if (id == null) {
      // TODO 校验是否存在后置任务
      throw new ServiceException("task.preTask.contains.canNotDelete", id);
    }
    taskRepository.deleteById(id);
  }

  @Override
  public Set<Long> findUnfinishedDemandIds(Long projectId, Set<Long> demandIds) {
    ScrumProject project = projectService.findById(projectId);
    ScrumTemplate templateInfo = templateService.findById(project.getTemplateId());
    List<ScrumTaskStep> taskSteps = new ArrayList<>();
    //        templateInfo.getTaskSteps().stream().sorted().collect(Collectors.toList());
    Long lastTaskStepId = taskSteps.get(taskSteps.size() - 1).getId();
    List<ScrumTask> allTasks = taskRepository.findAllByDemandIdIn(demandIds);
    Set<Long> hasTaskDemandIds = new HashSet<>();
    Set<Long> unfinishedDemandIds = new HashSet<>();
    allTasks.forEach(
        task -> {
          hasTaskDemandIds.add(task.getDemandId());
          if (!task.getTaskStepId().equals(lastTaskStepId)) {
            unfinishedDemandIds.add(task.getDemandId());
          }
        });
    demandIds.removeAll(hasTaskDemandIds);
    demandIds.addAll(unfinishedDemandIds);
    return demandIds;
  }

}
