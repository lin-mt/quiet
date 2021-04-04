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

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.validation.group.curd.Create;
import com.gitee.quiet.common.validation.group.curd.Update;
import com.gitee.quiet.common.validation.group.curd.single.DeleteSingle;
import com.gitee.quiet.system.entity.QuietDictionary;
import com.gitee.quiet.system.params.QuietDictionaryParam;
import com.gitee.quiet.system.service.QuietDictionaryService;
import com.querydsl.core.QueryResults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/dictionary")
public class QuietDictionaryController {
    
    private final QuietDictionaryService dictionaryService;
    
    public QuietDictionaryController(QuietDictionaryService dictionaryService) {
        this.dictionaryService = dictionaryService;
    }
    
    /**
     * 根据数据字典类型查询该类型的数据字典的树形结构提供选项
     *
     * @return 数据字典树形结构
     */
    @PostMapping("/treeByTypeForSelect")
    public Result<List<QuietDictionary>> treeByTypeForSelect(@RequestBody QuietDictionaryParam postParam) {
        return Result.success(dictionaryService.treeByTypeForSelect(postParam.getType()));
    }
    
    /**
     * 根据数据字典类型返回该类型的树形结构
     *
     * @return 数据字典树形结构
     */
    @PostMapping("/treeByType")
    public Result<List<QuietDictionary>> treeByType(@RequestBody QuietDictionaryParam postParam) {
        return Result.success(dictionaryService.treeByType(postParam.getType()));
    }
    
    /**
     * 分页查询数据字典.
     *
     * @return 查询的所有信息
     */
    @PostMapping("/page")
    public Result<QueryResults<QuietDictionary>> page(@RequestBody QuietDictionaryParam postParam) {
        return Result.success(dictionaryService.page(postParam.getParams(), postParam.page()));
    }
    
    /**
     * 新增数据字典.
     *
     * @param postParam :save 新增的数据字典信息
     * @return 新增后的数据字典信息
     */
    @PostMapping("/save")
    public Result<QuietDictionary> save(@RequestBody @Validated(Create.class) QuietDictionaryParam postParam) {
        return Result.createSuccess(dictionaryService.save(postParam.getSave()));
    }
    
    /**
     * 删除数据字典.
     *
     * @param postParam :deleteId 删除的数据字典ID
     * @return Result
     */
    @PostMapping("/delete")
    @PreAuthorize(value = "hasRole('Admin')")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) QuietDictionaryParam postParam) {
        dictionaryService.delete(postParam.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 更新数据字典.
     *
     * @param postParam :update 更新的数据字典信息
     * @return 新增后的数据字典信息
     */
    @PostMapping("/update")
    public Result<QuietDictionary> update(@RequestBody @Validated(Update.class) QuietDictionaryParam postParam) {
        return Result.updateSuccess(dictionaryService.update(postParam.getUpdate()));
    }
}
