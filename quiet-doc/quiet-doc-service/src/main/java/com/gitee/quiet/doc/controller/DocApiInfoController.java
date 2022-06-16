/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
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
