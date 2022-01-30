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

package com.gitee.quiet.doc.controller;

import com.gitee.quiet.doc.converter.DocApiConvert;
import com.gitee.quiet.doc.converter.DocApiGroupConvert;
import com.gitee.quiet.doc.converter.DocApiInfoConvert;
import com.gitee.quiet.doc.dto.DocApiDTO;
import com.gitee.quiet.doc.dubbo.UserDubboService;
import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.doc.entity.DocApiGroup;
import com.gitee.quiet.doc.entity.DocApiInfo;
import com.gitee.quiet.doc.service.DocApiGroupService;
import com.gitee.quiet.doc.service.DocApiInfoService;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.doc.vo.DocApiDetailVO;
import com.gitee.quiet.doc.vo.DocApiVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.system.entity.QuietUser;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接口信息.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class DocApiController {
    
    private final DocApiService apiService;
    
    private final DocApiGroupService apiGroupService;
    
    private final DocApiInfoService apiInfoService;
    
    private final DocApiConvert apiConvert;
    
    private final DocApiInfoConvert apiInfoConvert;
    
    private final DocApiGroupConvert apiGroupConvert;
    
    private final UserDubboService userDubboService;
    
    /**
     * 查询接口详细信息
     *
     * @param id 接口ID
     * @return 接口详细信息
     */
    @GetMapping("/detail/{id}")
    public Result<DocApiDetailVO> getDetail(@PathVariable Long id) {
        DocApiVO docApi = apiConvert.entity2vo(apiService.getById(id));
        if (docApi.getApiGroupId() != null) {
            DocApiGroup apiGroup = apiGroupService.findById(docApi.getApiGroupId());
            if (apiGroup != null) {
                docApi.setApiGroup(apiGroupConvert.entity2vo(apiGroup));
            }
        }
        QuietUser creator = userDubboService.getById(docApi.getCreator());
        if (creator != null) {
            docApi.setCreatorFullName(creator.getFullName());
        }
        DocApiDetailVO.DocApiDetailVOBuilder builder = DocApiDetailVO.builder();
        builder.api(docApi);
        DocApiInfo apiInfo = apiInfoService.getByApiId(id);
        if (apiInfo != null) {
            builder.apiInfo(apiInfoConvert.entity2vo(apiInfo));
        }
        return Result.success(builder.build());
    }
    
    /**
     * 新建接口
     *
     * @param dto 新建的接口信息
     * @return 新增的接口信息
     */
    @PostMapping
    public Result<DocApiVO> save(@RequestBody @Validated(Create.class) DocApiDTO dto) {
        DocApi save = apiService.save(apiConvert.dto2entity(dto));
        return Result.createSuccess(apiConvert.entity2vo(save));
    }
    
    /**
     * 更新接口信息
     *
     * @param dto 更新的接口信息
     * @return 更新后的接口信息
     */
    @PutMapping
    public Result<DocApiVO> update(@RequestBody @Validated(Update.class) DocApiDTO dto) {
        DocApi update = apiService.update(apiConvert.dto2entity(dto));
        return Result.updateSuccess(apiConvert.entity2vo(update));
    }
    
    /**
     * 根据接口ID删除接口信息
     *
     * @param id 要删除的接口ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        apiService.deleteById(id);
        return Result.deleteSuccess();
    }
}
