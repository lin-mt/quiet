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
import com.gitee.quiet.common.service.util.EntityUtils;
import com.gitee.quiet.doc.entity.DocApiBody;
import com.gitee.quiet.doc.repository.DocApiBodyRepository;
import com.gitee.quiet.doc.service.DocApiBodyService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Api Body Service 实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class DocApiBodyServiceImpl implements DocApiBodyService {
    
    private final DocApiBodyRepository apiQueryRepository;
    
    public DocApiBodyServiceImpl(DocApiBodyRepository apiQueryRepository) {
        this.apiQueryRepository = apiQueryRepository;
    }
    
    @Override
    public DocApiBody findByApiId(Long apiId) {
        if (apiId == null) {
            return null;
        }
        List<DocApiBody> apiBodies = apiQueryRepository.findAllByApiId(apiId);
        DocApiBody root = null;
        List<DocApiBody> children = new ArrayList<>();
        for (DocApiBody apiBody : apiBodies) {
            if (apiBody.getParentId() == null) {
                if (root != null) {
                    throw new ServiceException("api.body.root.moreThanOne");
                }
                root = apiBody;
                continue;
            }
            children.add(apiBody);
        }
        if (CollectionUtils.isNotEmpty(children) && root == null) {
            throw new ServiceException("api.body.not.rootInfo", apiId);
        }
        if (CollectionUtils.isEmpty(children) || root == null) {
            return root;
        }
        root.setChildren(EntityUtils.buildTreeData(children));
        return root;
    }
}
