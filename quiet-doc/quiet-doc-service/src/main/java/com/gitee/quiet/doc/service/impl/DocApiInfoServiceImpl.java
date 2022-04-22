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

import com.gitee.quiet.doc.entity.DocApiInfo;
import com.gitee.quiet.doc.repository.DocApiInfoRepository;
import com.gitee.quiet.doc.service.DocApiInfoService;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.service.exception.ServiceException;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * api信息服务实现类.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class DocApiInfoServiceImpl implements DocApiInfoService {

    private final DocApiInfoRepository repository;

    private final DocApiService apiService;

    public DocApiInfoServiceImpl(DocApiInfoRepository repository, @Lazy DocApiService apiService) {
        this.repository = repository;
        this.apiService = apiService;
    }

    @Override
    public DocApiInfo save(DocApiInfo save) {
        apiService.checkId(save.getApiId());
        if (repository.existsByApiId(save.getApiId())) {
            throw new ServiceException("apiInfo.apiId.exist", save.getApiId());
        }
        return repository.save(save);
    }

    @Override
    public DocApiInfo update(DocApiInfo update) {
        if (!repository.existsById(update.getId())) {
            throw new ServiceException("apiInfo.id.notExist", update.getId());
        }
        return repository.saveAndFlush(update);
    }

    @Override
    public DocApiInfo getByApiId(Long apiId) {
        return repository.getByApiId(apiId);
    }
}
