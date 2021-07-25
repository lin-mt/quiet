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

import com.gitee.quiet.doc.entity.DocApiFormData;
import com.gitee.quiet.doc.repository.DocApiFormDataRepository;
import com.gitee.quiet.doc.service.DocApiFormDataService;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Form Data Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class DocApiFormDataServiceImpl implements DocApiFormDataService {
    
    private final DocApiFormDataRepository apiFormDataRepository;
    
    public DocApiFormDataServiceImpl(DocApiFormDataRepository apiFormDataRepository) {
        this.apiFormDataRepository = apiFormDataRepository;
    }
    
    @Override
    public List<DocApiFormData> listByApiId(Long apiId) {
        if (apiId == null) {
            return Lists.newArrayList();
        }
        return apiFormDataRepository.findAllByApiId(apiId);
    }
}
