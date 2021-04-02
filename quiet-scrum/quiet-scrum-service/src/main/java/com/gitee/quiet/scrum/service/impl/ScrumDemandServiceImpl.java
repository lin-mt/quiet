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

import com.gitee.quiet.scrum.entity.ScrumDemand;
import com.gitee.quiet.scrum.repository.ScrumDemandRepository;
import com.gitee.quiet.scrum.service.ScrumDemandService;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 需求信息service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class ScrumDemandServiceImpl implements ScrumDemandService {
    
    private final ScrumDemandRepository demandRepository;
    
    public ScrumDemandServiceImpl(ScrumDemandRepository demandRepository) {
        this.demandRepository = demandRepository;
    }
    
    @Override
    public List<ScrumDemand> findAllByIteration(@NotNull Long iterationId) {
        return demandRepository.findAllByIterationId(iterationId);
    }
}
