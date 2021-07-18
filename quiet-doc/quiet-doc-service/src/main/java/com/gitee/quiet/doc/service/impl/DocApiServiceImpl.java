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

package com.gitee.quiet.doc.service.impl;

import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.doc.repository.DocApiRepository;
import com.gitee.quiet.doc.service.DocApiService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Api Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class DocApiServiceImpl implements DocApiService {
    
    private final DocApiRepository apiRepository;
    
    public DocApiServiceImpl(DocApiRepository apiRepository) {
        this.apiRepository = apiRepository;
    }
    
    @Override
    public List<DocApi> listAllByProjectId(Long projectId) {
        return apiRepository.findAllByProjectId(projectId);
    }
    
    @Override
    public void removeGroup(Long groupId) {
        List<DocApi> apis = apiRepository.findAllByGroupId(groupId);
        if (CollectionUtils.isNotEmpty(apis)) {
            apis.forEach(api -> api.getApiGroupIds().remove(groupId));
            apiRepository.saveAll(apis);
        }
    }
}
