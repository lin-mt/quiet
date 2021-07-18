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

import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.doc.entity.DocApiGroup;
import com.gitee.quiet.doc.repository.DocApiGroupRepository;
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.service.DocApiService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 文档分组Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class DocApiGroupServiceImpl implements DocApiGroupService {
    
    private final DocApiGroupRepository apiGroupRepository;
    
    private final DocApiService apiService;
    
    public DocApiGroupServiceImpl(DocApiGroupRepository apiGroupRepository, DocApiService apiService) {
        this.apiGroupRepository = apiGroupRepository;
        this.apiService = apiService;
    }
    
    @Override
    public DocApiGroup save(DocApiGroup save) {
        checkInfo(save);
        return apiGroupRepository.save(save);
    }
    
    @Override
    public DocApiGroup update(DocApiGroup update) {
        checkInfo(update);
        apiGroupRepository.findById(update.getId())
                .orElseThrow(() -> new ServiceException("api.group.id.notExist", update.getId()));
        return apiGroupRepository.saveAndFlush(update);
    }
    
    private void checkInfo(DocApiGroup apiGroup) {
        DocApiGroup exist = apiGroupRepository.findByProjectIdAndName(apiGroup.getProjectId(), apiGroup.getName());
        if (exist != null && !exist.getId().equals(apiGroup.getId())) {
            throw new ServiceException("api.group.name.exist", apiGroup.getProjectId(), apiGroup.getName());
        }
    }
    
    @Override
    public void deleteById(Long id) {
        apiGroupRepository.findById(id).orElseThrow(() -> new ServiceException("api.group.id.notExist", id));
        apiService.removeGroup(id);
        apiGroupRepository.deleteById(id);
    }
    
    @Override
    public List<DocApiGroup> listByProjectId(Long projectId) {
        return apiGroupRepository.findAllByProjectId(projectId);
    }
}
