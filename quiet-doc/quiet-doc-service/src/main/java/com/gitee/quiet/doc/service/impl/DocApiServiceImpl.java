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
import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.doc.repository.DocApiRepository;
import com.gitee.quiet.doc.service.DocApiBodyService;
import com.gitee.quiet.doc.service.DocApiFormDataService;
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.service.DocApiHeaderService;
import com.gitee.quiet.doc.service.DocApiPathParamService;
import com.gitee.quiet.doc.service.DocApiQueryService;
import com.gitee.quiet.doc.service.DocApiResponseService;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.doc.vo.DocApiDetail;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Lazy;
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
    
    private final DocApiGroupService apiGroupService;
    
    private final DocApiPathParamService apiPathParamService;
    
    private final DocApiBodyService apiBodyService;
    
    private final DocApiFormDataService apiFormDataService;
    
    private final DocApiHeaderService apiHeaderService;
    
    private final DocApiQueryService apiQueryService;
    
    private final DocApiResponseService apiResponseService;
    
    
    public DocApiServiceImpl(DocApiRepository apiRepository, @Lazy DocApiGroupService apiGroupService,
            DocApiPathParamService apiPathParamService, DocApiBodyService apiBodyService,
            DocApiFormDataService apiFormDataService, DocApiHeaderService apiHeaderService,
            DocApiQueryService apiQueryService, DocApiResponseService apiResponseService) {
        this.apiRepository = apiRepository;
        this.apiGroupService = apiGroupService;
        this.apiPathParamService = apiPathParamService;
        this.apiBodyService = apiBodyService;
        this.apiFormDataService = apiFormDataService;
        this.apiHeaderService = apiHeaderService;
        this.apiQueryService = apiQueryService;
        this.apiResponseService = apiResponseService;
    }
    
    @Override
    public List<DocApi> listAllByProjectId(Long projectId) {
        return apiRepository.findAllByProjectId(projectId);
    }
    
    @Override
    public void removeGroup(Long groupId) {
        List<DocApi> apis = apiRepository.findAllByApiGroupId(groupId);
        if (CollectionUtils.isNotEmpty(apis)) {
            apis.forEach(api -> api.setApiGroupId(null));
            apiRepository.saveAll(apis);
        }
    }
    
    @Override
    public DocApi save(DocApi save) {
        checkInfo(save);
        return apiRepository.save(save);
    }
    
    @Override
    public DocApi update(DocApi update) {
        checkInfo(update);
        return apiRepository.saveAndFlush(update);
    }
    
    private void checkInfo(DocApi api) {
        DocApi exist = apiRepository.findByProjectIdAndName(api.getProjectId(), api.getName());
        if (exist != null && !exist.getId().equals(api.getId())) {
            throw new ServiceException("api.name.exist", api.getProjectId(), api.getName());
        }
    }
    
    @Override
    public void deleteById(Long id) {
        apiRepository.findById(id).orElseThrow(() -> new ServiceException("api.id.notExist"));
        apiRepository.deleteById(id);
    }
    
    @Override
    public DocApiDetail getDetail(Long id) {
        DocApi docApi = apiRepository.findById(id).orElseThrow(() -> new ServiceException("api.id.notExist"));
        if (docApi.getApiGroupId() != null) {
            docApi.setApiGroup(apiGroupService.findById(docApi.getApiGroupId()));
        }
        // @formatter:off
        return DocApiDetail.builder()
                .api(docApi)
                .apiPathParam(apiPathParamService.listByApiId(id))
                .apiBody(apiBodyService.findByApiId(id))
                .apiFormData(apiFormDataService.listByApiId(id))
                .apiHeader(apiHeaderService.listByApiId(id))
                .apiQuery(apiQueryService.listByApiId(id))
                .apiResponse(apiResponseService.findByApiId(id))
                .build();
        // @formatter:on
    }
}
