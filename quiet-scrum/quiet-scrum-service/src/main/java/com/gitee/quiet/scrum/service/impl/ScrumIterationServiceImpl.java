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
import com.gitee.quiet.scrum.entity.ScrumIteration;
import com.gitee.quiet.scrum.repository.ScrumIterationRepository;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import com.gitee.quiet.scrum.service.ScrumVersionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 迭代信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumIterationServiceImpl implements ScrumIterationService {
    
    private final ScrumIterationRepository iterationRepository;
    
    private final ScrumVersionService versionService;
    
    public ScrumIterationServiceImpl(ScrumIterationRepository iterationRepository,
            @Lazy ScrumVersionService versionService) {
        this.iterationRepository = iterationRepository;
        this.versionService = versionService;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByVersionIds(Set<Long> versionIds) {
        if (CollectionUtils.isNotEmpty(versionIds)) {
            iterationRepository.deleteAllByVersionIdIn(versionIds);
        }
    }
    
    @Override
    public List<ScrumIteration> findAllByVersionIds(Set<Long> versionIds) {
        if (CollectionUtils.isNotEmpty(versionIds)) {
            return iterationRepository.findAllByVersionIdIn(versionIds);
        }
        return List.of();
    }
    
    @Override
    public ScrumIteration save(ScrumIteration save) {
        checkInfo(save);
        return iterationRepository.save(save);
    }
    
    @Override
    public ScrumIteration update(ScrumIteration update) {
        checkInfo(update);
        return iterationRepository.saveAndFlush(update);
    }
    
    private void checkInfo(@NotNull ScrumIteration iteration) {
        versionService.checkIdExist(iteration.getVersionId());
        ScrumIteration exist = iterationRepository
                .findByVersionIdAndName(iteration.getVersionId(), iteration.getName());
        if (exist != null && !exist.getId().equals(iteration.getId())) {
            throw new ServiceException("iteration.versionId.name.exist", iteration.getVersionId(), iteration.getName());
        }
    }
}
