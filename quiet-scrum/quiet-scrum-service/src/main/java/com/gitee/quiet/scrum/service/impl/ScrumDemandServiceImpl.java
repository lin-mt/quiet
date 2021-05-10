/*
 * Copyright 2021 lin-mt@outlook.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gitee.quiet.scrum.service.impl;

import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.common.service.jpa.SelectBooleanBuilder;
import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.repository.ScrumDemandRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
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
    
    public ScrumDemandServiceImpl(JPAQueryFactory jpaQueryFactory, ScrumDemandRepository demandRepository,
            ScrumTaskService taskService, @Lazy ScrumIterationService iterationService) {
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
    public QueryResults<ScrumDemand> page(ScrumDemand params, Pageable page) {
        return SelectBuilder.booleanBuilder(params).from(jpaQueryFactory, scrumDemand, page);
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
    public List<ScrumDemand> listToBePlanned(Long projectId, Long offset, Long limit) {
        JPAQuery<ScrumDemand> query = SelectBooleanBuilder.booleanBuilder().notNullEq(projectId, scrumDemand.projectId)
                .and(scrumDemand.iterationId.isNull()).from(jpaQueryFactory, scrumDemand)
                .orderBy(scrumDemand.gmtCreate.desc());
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
        JPAQuery<ScrumDemand> query = SelectBooleanBuilder.booleanBuilder()
                .notNullEq(iterationId, scrumDemand.iterationId).from(jpaQueryFactory, scrumDemand);
        if (!Long.valueOf(0).equals(limit)) {
            query.offset(offset == null ? 0 : offset).limit(limit);
        }
        return query.fetch();
    }
    
    @Override
    public void deleteById(Long id) {
        ScrumDemand delete = demandRepository.getOne(id);
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
    
    private void checkDemand(@NotNull ScrumDemand demand) {
        ScrumDemand exist = demandRepository.findByProjectIdAndTitle(demand.getProjectId(), demand.getTitle());
        if (exist != null && !exist.getId().equals(demand.getId())) {
            throw new ServiceException("demand.in.project.title.exist", demand.getTitle());
        }
        if (demand.getParentId() != null && !demandRepository.existsById(demand.getParentId())) {
            throw new ServiceException("demand.parentId.not.exist", demand.getParentId());
        }
        if (demand.getIterationId() != null) {
            iterationService.checkIdExist(demand.getIterationId());
        }
        if (demand.getOptimizeDemandId() != null && !demandRepository.existsById(demand.getOptimizeDemandId())) {
            throw new ServiceException("demand.optimizeDemandId.not.exist", demand.getOptimizeDemandId());
        }
    }
}
