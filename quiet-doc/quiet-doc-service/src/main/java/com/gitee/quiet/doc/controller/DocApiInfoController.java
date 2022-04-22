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

import com.gitee.quiet.doc.converter.DocApiInfoConvert;
import com.gitee.quiet.doc.dto.DocApiInfoDTO;
import com.gitee.quiet.doc.entity.DocApiInfo;
import com.gitee.quiet.doc.service.DocApiInfoService;
import com.gitee.quiet.doc.vo.DocApiInfoVO;
import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * api信息Controller.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/api-info")
public class DocApiInfoController {

    private final DocApiInfoConvert convert;

    private final DocApiInfoService service;

    /**
     * 新建apiInfo
     *
     * @param dto 新建的apiInfo信息
     * @return 新增的apiInfo信息
     */
    @PostMapping
    public Result<DocApiInfoVO> save(@RequestBody @Validated(Create.class) DocApiInfoDTO dto) {
        DocApiInfo save = service.save(convert.dto2entity(dto));
        return Result.success(convert.entity2vo(save));
    }

    /**
     * 更新apiInfo信息
     *
     * @param dto 更新的apiInfo信息
     * @return 更新后的apiInfo信息
     */
    @PutMapping
    public Result<DocApiInfoVO> update(@RequestBody @Validated(Update.class) DocApiInfoDTO dto) {
        DocApiInfo update = service.update(convert.dto2entity(dto));
        return Result.updateSuccess(convert.entity2vo(update));
    }

}
