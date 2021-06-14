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

package com.gitee.quiet.scrum.controller;

import com.gitee.quiet.common.base.result.Result;
import com.gitee.quiet.common.validation.group.param.IdValid;
import com.gitee.quiet.common.validation.group.param.curd.Create;
import com.gitee.quiet.common.validation.group.param.curd.Update;
import com.gitee.quiet.common.validation.group.param.curd.single.DeleteSingle;
import com.gitee.quiet.scrum.entity.ScrumTemplate;
import com.gitee.quiet.scrum.params.ScrumTemplateParam;
import com.gitee.quiet.scrum.service.ScrumTemplateService;
import com.gitee.quiet.scrum.vo.AllTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目模板Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@RequestMapping("/template")
public class ScrumTemplateController {
    
    private final ScrumTemplateService templateService;
    
    public ScrumTemplateController(ScrumTemplateService templateService) {
        this.templateService = templateService;
    }
    
    /**
     * 查询所有的模板信息
     *
     * @return 根据是否创建人创建的模板进行分组
     */
    @PostMapping("/allTemplates")
    public Result<AllTemplate> allTemplates() {
        return Result.success(templateService.allTemplates());
    }
    
    /**
     * 获取模板信息
     *
     * @param param :id 模板ID
     * @return 模板信息
     */
    @PostMapping("/templateInfo")
    public Result<ScrumTemplate> templateInfo(@RequestBody @Validated(IdValid.class) ScrumTemplateParam param) {
        return Result.success(templateService.templateInfo(param.getId()));
    }
    
    /**
     * 新增模板
     *
     * @param param :save 新增的模板信息
     * @return 新增后的模板信息
     */
    @PostMapping("/save")
    public Result<ScrumTemplate> save(@RequestBody @Validated(Create.class) ScrumTemplateParam param) {
        return Result.createSuccess(templateService.save(param.getSave()));
    }
    
    /**
     * 更新模板
     *
     * @param param :update 更新的模板信息
     * @return 更新后的模板信息
     */
    @PostMapping("/update")
    public Result<ScrumTemplate> update(@RequestBody @Validated(Update.class) ScrumTemplateParam param) {
        return Result.updateSuccess(templateService.update(param.getUpdate()));
    }
    
    /**
     * 删除模板
     *
     * @param param :deleteId 删除的模板ID
     * @return 删除结果
     */
    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody @Validated(DeleteSingle.class) ScrumTemplateParam param) {
        templateService.deleteById(param.getDeleteId());
        return Result.deleteSuccess();
    }
    
    /**
     * 根据模板名称查询启用的模板信息
     *
     * @param param 查询参数
     * @return 查询结果
     */
    @PostMapping("/listEnabledByName")
    public Result<List<ScrumTemplate>> listEnabledByName(@RequestBody ScrumTemplateParam param) {
        return Result.success(templateService.listEnabledByName(param.getName(), 9L));
    }
}
