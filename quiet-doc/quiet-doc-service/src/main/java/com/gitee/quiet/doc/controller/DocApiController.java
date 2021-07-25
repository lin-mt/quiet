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

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.validation.group.Create;
import com.gitee.quiet.common.validation.group.Update;
import com.gitee.quiet.doc.converter.DocApiConvert;
import com.gitee.quiet.doc.dto.DocApiDto;
import com.gitee.quiet.doc.entity.DocApi;
import com.gitee.quiet.doc.service.DocApiService;
import com.gitee.quiet.doc.vo.DocApiDetail;
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
    
    private final DocApiConvert apiConvert;
    
    /**
     * 查询接口详细信息
     *
     * @param id 接口ID
     * @return 接口详细信息
     */
    @GetMapping("/detail/{id}")
    public Result<DocApiDetail> getDetail(@PathVariable Long id) {
        return Result.success(apiService.getDetail(id));
    }
    
    /**
     * 新建接口
     *
     * @param dto 新建的接口信息
     * @return 新增的接口信息
     */
    @PostMapping
    public Result<DocApi> save(@RequestBody @Validated(Create.class) DocApiDto dto) {
        return Result.success(apiService.save(apiConvert.dtoToEntity(dto)));
    }
    
    /**
     * 更新接口信息
     *
     * @param dto 更新的接口信息
     * @return 更新后的接口信息
     */
    @PutMapping
    public Result<DocApi> update(@RequestBody @Validated(Update.class) DocApiDto dto) {
        return Result.success(apiService.update(apiConvert.dtoToEntity(dto)));
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
