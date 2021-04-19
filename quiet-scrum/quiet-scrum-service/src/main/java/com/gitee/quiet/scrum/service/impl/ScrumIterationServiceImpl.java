/*
 * Copyright 2021. lin-mt@outlook.com
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

import com.gitee.quiet.scrum.entity.ScrumIteration;
import com.gitee.quiet.scrum.repository.ScrumIterationRepository;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    
    public ScrumIterationServiceImpl(ScrumIterationRepository iterationRepository) {
        this.iterationRepository = iterationRepository;
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
}
