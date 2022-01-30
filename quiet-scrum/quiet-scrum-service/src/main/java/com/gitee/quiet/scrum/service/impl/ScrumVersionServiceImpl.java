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

import com.gitee.quiet.jpa.utils.EntityUtils;
import com.gitee.quiet.scrum.entity.ScrumIteration;
import com.gitee.quiet.scrum.entity.ScrumVersion;
import com.gitee.quiet.scrum.repository.ScrumVersionRepository;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import com.gitee.quiet.scrum.service.ScrumVersionService;
import com.gitee.quiet.service.exception.ServiceException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 版本信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumVersionServiceImpl implements ScrumVersionService {
    
    private final ScrumVersionRepository versionRepository;
    
    private final ScrumIterationService iterationService;
    
    public ScrumVersionServiceImpl(ScrumVersionRepository versionRepository, ScrumIterationService iterationService) {
        this.versionRepository = versionRepository;
        this.iterationService = iterationService;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllByProjectId(@NotNull Long projectId) {
        List<ScrumVersion> versions = versionRepository.findAllByProjectId(projectId);
        if (CollectionUtils.isNotEmpty(versions)) {
            // 删除版本信息下的迭代信息
            Set<Long> versionIds = versions.stream().map(ScrumVersion::getId).collect(Collectors.toSet());
            iterationService.deleteByVersionIds(versionIds);
            versionRepository.deleteByProjectId(projectId);
        }
    }
    
    @Override
    public List<ScrumVersion> findDetailsByProjectId(Long projectId) {
        List<ScrumVersion> versions = versionRepository.findAllByProjectId(projectId);
        if (CollectionUtils.isNotEmpty(versions)) {
            Set<Long> versionIds = versions.stream().map(ScrumVersion::getId).collect(Collectors.toSet());
            List<ScrumIteration> iterations = iterationService.findAllByVersionIds(versionIds);
            Map<Long, List<ScrumIteration>> versionIdToIterations = iterations.stream()
                    .collect(Collectors.groupingBy(ScrumIteration::getVersionId));
            for (ScrumVersion version : versions) {
                version.setIterations(versionIdToIterations.getOrDefault(version.getId(), List.of()));
            }
        }
        return EntityUtils.buildTreeData(versions);
    }
    
    @Override
    public ScrumVersion save(ScrumVersion save) {
        checkInfo(save);
        return versionRepository.save(save);
    }
    
    @Override
    public ScrumVersion update(ScrumVersion update) {
        checkInfo(update);
        return versionRepository.saveAndFlush(update);
    }
    
    @Override
    public void checkIdExist(Long id) {
        if (!versionRepository.existsById(id)) {
            throw new ServiceException("version.id.not.exist");
        }
    }
    
    @Override
    public void deleteById(Long id) {
        if (versionRepository.countByParentId(id) > 0) {
            throw new ServiceException("version.hasChild.canNotDelete", id);
        }
        if (iterationService.countByVersionId(id) > 0) {
            throw new ServiceException("version.hasIteration.canNotDelete", id);
        }
        versionRepository.deleteById(id);
    }
    
    @Override
    public ScrumVersion nextVersion(Long id) {
        ScrumVersion currentVersion = versionRepository.findById(id)
                .orElseThrow(() -> new ServiceException("version.id.not.exist"));
        return versionRepository.findFirstByPlanStartDateAfterOrderByPlanEndDateAsc(currentVersion.getPlanStartDate());
    }
    
    private void checkInfo(@NotNull ScrumVersion version) {
        ScrumVersion exist = versionRepository.findByProjectIdAndName(version.getProjectId(), version.getName());
        if (exist != null && !exist.getName().equals(version.getName())) {
            throw new ServiceException("version.project.name.exist", version.getProjectId(), version.getName());
        }
        if (version.getParentId() != null && !versionRepository.existsById(version.getParentId())) {
            throw new ServiceException("version.id.not.exist", version.getParentId());
        }
    }
}
