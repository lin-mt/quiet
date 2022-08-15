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
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.filter.ScrumDemandFilter;
import com.gitee.quiet.scrum.repository.ScrumDemandRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gitee.quiet.scrum.entity.QScrumDemand.scrumDemand;

/**
 * 需求信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumDemandServiceImpl implements ScrumDemandService {

  private final JPAQueryFactory jpaQueryFactory;

  private final ScrumDemandRepository demandRepository;

  private final ScrumTaskService taskService;

  private final ScrumIterationService iterationService;

  public ScrumDemandServiceImpl(
      JPAQueryFactory jpaQueryFactory,
      ScrumDemandRepository demandRepository,
      ScrumTaskService taskService,
      @Lazy ScrumIterationService iterationService) {
    this.jpaQueryFactory = jpaQueryFactory;
    this.demandRepository = demandRepository;
    this.taskService = taskService;
    this.iterationService = iterationService;
  }

  @Override
  public List<ScrumDemand> findAllByIterationId(@Validated @NotNull Long iterationId) {
    return demandRepository.findAllByIterationId(iterationId);
  }

  @Override
  public Page<ScrumDemand> page(ScrumDemand params, Pageable page) {
    BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
    return demandRepository.findAll(predicate, page);
  }

  @Override
  public ScrumDemand save(@Validated(Create.class) @NotNull ScrumDemand save) {
    checkDemand(save);
    return demandRepository.save(save);
  }

  @Override
  public ScrumDemand update(@Validated(Update.class) @NotNull ScrumDemand update) {
    checkDemand(update);
    return demandRepository.save(update);
  }

  @Override
  public void deleteAllByProjectId(@NotNull Long projectId) {
    List<ScrumDemand> demands = demandRepository.findAllByProjectId(projectId);
    if (CollectionUtils.isNotEmpty(demands)) {
      Set<Long> demandIds = demands.stream().map(ScrumDemand::getId).collect(Collectors.toSet());
      taskService.deleteAllByDemandIds(demandIds);
    }
  }

  @Override
  public long countByPriorityId(Long priorityId) {
    return demandRepository.countByPriorityId(priorityId);
  }

  @Override
  public List<ScrumDemand> listToBePlanned(
      Long projectId, ScrumDemandFilter filter, Long offset, Long limit) {
    // @formatter:off
    JPAQuery<ScrumDemand> query =
        SelectBooleanBuilder.booleanBuilder()
            .notNullEq(projectId, scrumDemand.projectId)
            .notNullEq(filter.getDemandType(), scrumDemand.type)
            .notNullEq(filter.getPriorityId(), scrumDemand.priorityId)
            .with(
                builder -> {
                  if (filter.getPlanned() != null) {
                    builder.and(
                        filter.getPlanned()
                            ? scrumDemand.iterationId.isNotNull()
                            : scrumDemand.iterationId.isNull());
                  }
                })
            .from(jpaQueryFactory, scrumDemand)
            .orderBy(scrumDemand.gmtCreate.desc());
    // @formatter:on
    if (!Long.valueOf(0).equals(limit)) {
      query.offset(offset == null ? 0 : offset).limit(limit);
    }
    return query.fetch();
  }

  @Override
  public long countByIterationId(Long iterationId) {
    return demandRepository.countByIterationId(iterationId);
  }

  @Override
  public List<ScrumDemand> scrollIteration(Long iterationId, Long offset, Long limit) {
    JPAQuery<ScrumDemand> query =
        SelectBooleanBuilder.booleanBuilder()
            .notNullEq(iterationId, scrumDemand.iterationId)
            .from(jpaQueryFactory, scrumDemand);
    if (!Long.valueOf(0).equals(limit)) {
      query.offset(offset == null ? 0 : offset).limit(limit);
    }
    return query.fetch();
  }

  @Override
  public void deleteById(Long id) {
    ScrumDemand delete = demandRepository.getById(id);
    if (delete.getIterationId() != null) {
      throw new ServiceException("demand.iterationId.notNull.canNotDelete");
    }
    demandRepository.deleteById(id);
  }

  @Override
  public void checkIdExist(Long id) {
    if (!demandRepository.existsById(id)) {
      throw new ServiceException("demand.id.notExist", id);
    }
  }

  @Override
  public List<ScrumDemand> findAllUnfinished(Long iterationId) {
    List<ScrumDemand> allDemand = demandRepository.findAllByIterationId(iterationId);
    if (CollectionUtils.isNotEmpty(allDemand)) {
      Set<Long> unfinishedDemandIds =
          taskService.findUnfinishedDemandIds(
              allDemand.get(0).getProjectId(),
              allDemand.stream().map(ScrumDemand::getId).collect(Collectors.toSet()));
      if (CollectionUtils.isNotEmpty(unfinishedDemandIds)) {
        return allDemand.stream()
            .filter(demand -> unfinishedDemandIds.contains(demand.getId()))
            .collect(Collectors.toList());
      }
    }
    return List.of();
  }

  @Override
  public void saveAll(List<ScrumDemand> demands) {
    demandRepository.saveAll(demands);
  }

  private void checkDemand(@NotNull ScrumDemand demand) {
    ScrumDemand exist =
        demandRepository.findByProjectIdAndTitle(demand.getProjectId(), demand.getTitle());
    if (exist != null && !exist.getId().equals(demand.getId())) {
      throw new ServiceException("demand.in.project.title.exist", demand.getTitle());
    }
    if (demand.getParentId() != null && !demandRepository.existsById(demand.getParentId())) {
      throw new ServiceException("demand.parentId.not.exist", demand.getParentId());
    }
    if (demand.getIterationId() != null) {
      iterationService.checkIdExist(demand.getIterationId());
    }
    if (demand.getOptimizeDemandId() != null
        && !demandRepository.existsById(demand.getOptimizeDemandId())) {
      throw new ServiceException("demand.optimizeDemandId.not.exist", demand.getOptimizeDemandId());
    }
  }
}
