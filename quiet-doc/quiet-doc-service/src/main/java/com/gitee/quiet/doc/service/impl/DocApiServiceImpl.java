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
import com.gitee.quiet.service.exception.ServiceException;
import java.util.Collection;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * Api Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class DocApiServiceImpl implements DocApiService {

    private final DocApiRepository repository;

    public DocApiServiceImpl(DocApiRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<DocApi> listAllByProjectId(Long projectId) {
        return repository.findAllByProjectId(projectId);
    }

    @Override
    public void removeGroup(Long groupId) {
        List<DocApi> apis = repository.findAllByApiGroupId(groupId);
        if (CollectionUtils.isNotEmpty(apis)) {
            apis.forEach(api -> api.setApiGroupId(null));
            repository.saveAll(apis);
        }
    }

    @Override
    public DocApi save(DocApi save) {
        checkInfo(save);
        return repository.save(save);
    }

    @Override
    public DocApi update(DocApi update) {
        checkInfo(update);
        return repository.saveAndFlush(update);
    }

    private void checkInfo(DocApi api) {
        DocApi exist = repository.findByProjectIdAndName(api.getProjectId(), api.getName());
        if (exist != null && !exist.getId().equals(api.getId())) {
            throw new ServiceException("api.name.exist", api.getProjectId(), api.getName());
        }
    }

    @Override
    public void deleteById(Long id) {

        repository.findById(id).orElseThrow(() -> new ServiceException("api.id.notExist"));
        repository.deleteById(id);
    }

    @Override
    public DocApi getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ServiceException("api.id.notExist"));
    }

    @Override
    public void checkId(Long id) {
        if (!repository.existsById(id)) {
            throw new ServiceException("api.id.notExist");
        }
    }

    @Override
    public void saveAll(Collection<DocApi> docApis) {
        if (CollectionUtils.isEmpty(docApis)) {
            return;
        }
        repository.saveAll(docApis);
    }
}
