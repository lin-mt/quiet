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
import com.gitee.quiet.scrum.entity.ScrumTask;
import com.gitee.quiet.scrum.repository.ScrumTaskRepository;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import com.gitee.quiet.service.exception.ServiceException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gitee.quiet.scrum.entity.QScrumTask.scrumTask;

/**
 * 任务信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class ScrumTaskServiceImpl implements ScrumTaskService {

  private final JPAQueryFactory jpaQueryFactory;
  private final ScrumTaskRepository taskRepository;

  @Override
  public List<ScrumTask> list(Set<Long> demandIds, Set<Long> executorIds) {
    if (CollectionUtils.isEmpty(demandIds)) {
      return List.of();
    }
    BooleanBuilder predicate =
        SelectBooleanBuilder.booleanBuilder()
            .notEmptyIn(demandIds, scrumTask.demandId)
            .notEmptyIn(executorIds, scrumTask.executorId)
            .getPredicate();
    return jpaQueryFactory.selectFrom(scrumTask).where(predicate).fetch();
  }

  @Override
  public void deleteById(Long id) {
    if (id == null) {
      return;
    }
    BooleanBuilder predicate =
        SelectBooleanBuilder.booleanBuilder().findInSet(id, scrumTask.preTaskIds).getPredicate();
    List<ScrumTask> afterTasks = jpaQueryFactory.selectFrom(scrumTask).where(predicate).fetch();
    if (CollectionUtils.isNotEmpty(afterTasks)) {
      throw new ServiceException(
          "task.preTask.contains.canNotDelete",
          afterTasks.stream()
              .map(ScrumTask::getId)
              .map(Objects::toString)
              .collect(Collectors.joining(",")));
    }
    taskRepository.deleteById(id);
  }

  @Override
  public List<ScrumTask> listAllByDemandId(Long demandId) {
    return taskRepository.findAllByDemandId(demandId);
  }

  @Override
  public Long countByTaskStepIdIn(Set<Long> taskStepIds) {
    if (CollectionUtils.isEmpty(taskStepIds)) {
      return 0L;
    }
    return taskRepository.countByTaskStepIdIn(taskStepIds);
  }
}
