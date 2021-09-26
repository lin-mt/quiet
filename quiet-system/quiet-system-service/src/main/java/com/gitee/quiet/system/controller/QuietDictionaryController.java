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

package com.gitee.quiet.system.controller;

import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.system.convert.QuietDictionaryConvert;
import com.gitee.quiet.system.dto.QuietDictionaryDTO;
import com.gitee.quiet.system.entity.QuietDictionary;
import com.gitee.quiet.system.service.QuietDictionaryService;
import com.gitee.quiet.validation.groups.Create;
import com.gitee.quiet.validation.groups.Update;
import com.querydsl.core.QueryResults;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据字典Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/dictionary")
public class QuietDictionaryController {
    
    private final QuietDictionaryService dictionaryService;
    
    private final QuietDictionaryConvert dictionaryConvert;
    
    /**
     * 根据数据字典类型查询该类型的数据字典，不包含一级数据字典，type 为空时返回空的集合
     *
     * @param dto :type 数据字典类型
     * @return 数据字典
     */
    @GetMapping("/listByTypeForSelect")
    public Result<List<QuietDictionary>> listByTypeForSelect(QuietDictionaryDTO dto) {
        return Result.success(dictionaryService.listByTypeForSelect(dto.getType()));
    }
    
    /**
     * 根据数据字典类型返回该类型所有字典信息，包含一级数据字典，type 为空的时候可以查询所有字典信息
     *
     * @param dto :type 数据字典类型
     * @return 数据字典信息
     */
    @GetMapping("/treeByType")
    public Result<List<QuietDictionary>> treeByType(QuietDictionaryDTO dto) {
        return Result.success(dictionaryService.treeByType(dto.getType()));
    }
    
    /**
     * 分页查询数据字典.
     *
     * @return 查询的所有信息
     */
    @GetMapping("/page")
    public Result<QueryResults<QuietDictionary>> page(QuietDictionaryDTO dto) {
        return Result.success(dictionaryService.page(dictionaryConvert.dtoToEntity(dto), dto.page()));
    }
    
    /**
     * 新增数据字典.
     *
     * @param dto 新增的数据字典信息
     * @return 新增后的数据字典信息
     */
    @PostMapping
    public Result<QuietDictionary> save(@RequestBody @Validated(Create.class) QuietDictionaryDTO dto) {
        return Result.createSuccess(dictionaryService.save(dictionaryConvert.dtoToEntity(dto)));
    }
    
    /**
     * 删除数据字典.
     *
     * @param id 删除的数据字典ID
     * @return Result
     */
    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('Admin')")
    public Result<Object> delete(@PathVariable Long id) {
        dictionaryService.delete(id);
        return Result.deleteSuccess();
    }
    
    /**
     * 更新数据字典.
     *
     * @param dto 更新的数据字典信息
     * @return 新增后的数据字典信息
     */
    @PutMapping
    public Result<QuietDictionary> update(@RequestBody @Validated(Update.class) QuietDictionaryDTO dto) {
        return Result.updateSuccess(dictionaryService.update(dictionaryConvert.dtoToEntity(dto)));
    }
}
