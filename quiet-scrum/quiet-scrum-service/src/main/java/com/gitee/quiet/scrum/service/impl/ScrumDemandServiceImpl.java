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
import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.repository.ScrumDemandRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import com.gitee.quiet.scrum.service.ScrumTaskService;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
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
    
    public ScrumDemandServiceImpl(JPAQueryFactory jpaQueryFactory, ScrumDemandRepository demandRepository,
            ScrumTaskService taskService) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.demandRepository = demandRepository;
        this.taskService = taskService;
    }
    
    @Override
    public List<ScrumDemand> findAllByIteration(@Validated @NotNull Long iterationId) {
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
        return demandRepository.findAllToBePlanned(projectId, offset, limit);
    }
    
    @Override
    public long countByIterationId(Long iterationId) {
        return demandRepository.countByIterationId(iterationId);
    }
    
    private void checkDemand(@NotNull ScrumDemand demand) {
        ScrumDemand exist = demandRepository.findByProjectIdAndTitle(demand.getProjectId(), demand.getTitle());
        if (exist != null && !exist.getId().equals(demand.getId())) {
            throw new ServiceException("demand.in.project.title.exist", demand.getTitle());
        }
        if (demand.getParentId() != null && !demandRepository.existsById(demand.getParentId())) {
            throw new ServiceException("demand.parentId.not.exist", demand.getParentId());
        }
        if (demand.getOptimizeDemandId() != null && !demandRepository.existsById(demand.getOptimizeDemandId())) {
            throw new ServiceException("demand.optimizeDemandId.not.exist", demand.getOptimizeDemandId());
        }
    }
}
