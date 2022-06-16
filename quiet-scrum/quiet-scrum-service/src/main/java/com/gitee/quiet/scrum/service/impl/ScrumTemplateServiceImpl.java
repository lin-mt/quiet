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
import com.gitee.quiet.scrum.entity.ScrumPriority;
import com.gitee.quiet.scrum.entity.ScrumTaskStep;
import com.gitee.quiet.scrum.entity.ScrumTemplate;
import com.gitee.quiet.scrum.repository.ScrumTemplateRepository;
import com.gitee.quiet.scrum.service.ScrumPriorityService;
import com.gitee.quiet.scrum.service.ScrumProjectService;
import com.gitee.quiet.scrum.service.ScrumTaskStepService;
import com.gitee.quiet.scrum.service.ScrumTemplateService;
import com.gitee.quiet.scrum.vo.AllTemplate;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.service.utils.CurrentUserUtil;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.gitee.quiet.scrum.entity.QScrumTemplate.scrumTemplate;

/**
 * 模板信息Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumTemplateServiceImpl implements ScrumTemplateService {

    private final ScrumTemplateRepository templateRepository;

    private final JPAQueryFactory jpaQueryFactory;

    private final ScrumTaskStepService taskStepService;

    private final ScrumProjectService projectService;

    private final ScrumPriorityService priorityService;

    public ScrumTemplateServiceImpl(ScrumTemplateRepository templateRepository, JPAQueryFactory jpaQueryFactory,
        ScrumTaskStepService taskStepService, ScrumProjectService projectService,
        ScrumPriorityService priorityService) {
        this.templateRepository = templateRepository;
        this.jpaQueryFactory = jpaQueryFactory;
        this.taskStepService = taskStepService;
        this.projectService = projectService;
        this.priorityService = priorityService;
    }

    @Override
    public AllTemplate allTemplates() {
        Long currentUserId = CurrentUserUtil.getId();
        List<ScrumTemplate> templates = templateRepository.findAllByEnabledOrCreator(true, currentUserId);
        Map<Long, List<ScrumTaskStep>> templateIdToTaskSteps = new HashMap<>(templates.size());
        Map<Long, List<ScrumPriority>> templateIdToPriorities = new HashMap<>(templates.size());
        if (CollectionUtils.isNotEmpty(templates)) {
            Set<Long> templateIds = templates.stream().map(ScrumTemplate::getId).collect(Collectors.toSet());
            templateIdToTaskSteps = taskStepService.findAllByTemplateIds(templateIds);
            templateIdToPriorities = priorityService.findAllByTemplateIds(templateIds);
        }
        AllTemplate allTemplate = new AllTemplate();
        for (ScrumTemplate template : templates) {
            template.setTaskSteps(templateIdToTaskSteps.get(template.getId()));
            template.setPriorities(templateIdToPriorities.get(template.getId()));
            if (template.getCreator().equals(currentUserId)) {
                allTemplate.getTemplateCreated().add(template);
            } else {
                allTemplate.getTemplateSelectable().add(template);
            }
        }
        return allTemplate;
    }

    @Override
    public ScrumTemplate save(ScrumTemplate save) {
        checkInfo(save);
        return templateRepository.save(save);
    }

    @Override
    public ScrumTemplate update(ScrumTemplate update) {
        checkInfo(update);
        return templateRepository.saveAndFlush(update);
    }

    @Override
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

    @Override
    public List<ScrumTemplate> listEnabledByName(String name, long limit) {
        JPAQuery<ScrumTemplate> query = SelectBooleanBuilder.booleanBuilder().notBlankContains(name, scrumTemplate.name)
            .and(scrumTemplate.enabled.eq(true)).from(jpaQueryFactory, scrumTemplate);
        if (limit > 0) {
            query.limit(limit);
        }
        return query.fetch();
    }

    @Override
    public List<ScrumTemplate> findAllByIds(Set<Long> ids) {
        return templateRepository.findAllById(ids);
    }

    @Override
    public ScrumTemplate findById(Long id) {
        return templateRepository.findById(id).orElseThrow(() -> new ServiceException("template.id.not.exist", id));
    }

    @Override
    public boolean existsById(Long id) {
        return templateRepository.existsById(id);
    }

    @Override
    public ScrumTemplate templateInfo(Long id) {
        ScrumTemplate template = findById(id);
        template.setTaskSteps(taskStepService.findAllByTemplateId(id));
        template.setPriorities(priorityService.findAllByTemplateId(id));
        return template;
    }

    /**
     * 校验保存的信息.
     *
     * @param template 保存的模板信息
     */
    public void checkInfo(@NotNull ScrumTemplate template) {
        ScrumTemplate exist = templateRepository.findByName(template.getName());
        if (exist != null && !exist.getName().equals(template.getName())) {
            throw new ServiceException("template.name.exist", template.getName());
        }
        if (BooleanUtils.toBoolean(template.getEnabled())) {
            if (template.getId() == null || taskStepService.countByTemplateId(template.getId()) == 0) {
                throw new ServiceException("template.nonTaskStep.canNotEnable", template.getId());
            }
            if (template.getId() == null || priorityService.countByTemplateId(template.getId()) == 0) {
                throw new ServiceException("template.nonPriority.canNotEnable", template.getId());
            }
        }
    }
}
