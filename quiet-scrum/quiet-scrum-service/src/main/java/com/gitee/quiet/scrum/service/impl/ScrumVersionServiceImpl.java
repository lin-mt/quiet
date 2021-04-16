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

import com.gitee.quiet.scrum.entity.ScrumVersion;
import com.gitee.quiet.scrum.repository.ScrumVersionRepository;
import com.gitee.quiet.scrum.service.ScrumIterationService;
import com.gitee.quiet.scrum.service.ScrumVersionService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.List;
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
        List<ScrumVersion> versions = versionRepository.findAllByParentId(projectId);
        if (CollectionUtils.isNotEmpty(versions)) {
            // 删除版本信息下的迭代信息
            Set<Long> versionIds = versions.stream().map(ScrumVersion::getId).collect(Collectors.toSet());
            iterationService.deleteByVersionIds(versionIds);
            versionRepository.deleteByProjectId(projectId);
        }
    }
}
