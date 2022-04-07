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

import com.gitee.quiet.scrum.convert.ScrumTemplateConvert;
import com.gitee.quiet.scrum.dto.ScrumTemplateDTO;
import com.gitee.quiet.scrum.entity.ScrumTemplate;
import com.gitee.quiet.scrum.service.ScrumTemplateService;
import com.gitee.quiet.scrum.vo.AllTemplate;
import com.gitee.quiet.scrum.vo.ScrumTemplateVO;
import com.gitee.quiet.service.result.Result;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目模板Controller.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/template")
public class ScrumTemplateController {
    
    private final ScrumTemplateService templateService;
    
    private final ScrumTemplateConvert templateConvert;
    
    /**
     * 查询所有的模板信息.
     *
     * @return 根据是否创建人创建的模板进行分组
     */
    @GetMapping("/all-templates")
    public Result<AllTemplate> allTemplates() {
        return Result.success(templateService.allTemplates());
    }
    
    /**
     * 获取模板信息.
     *
     * @param id 模板ID
     * @return 模板信息
     */
    @GetMapping("/{id}")
    public Result<ScrumTemplateVO> templateInfo(@PathVariable Long id) {
        ScrumTemplate scrumTemplate = templateService.templateInfo(id);
        return Result.success(templateConvert.entity2vo(scrumTemplate));
    }
    
    /**
     * 新增模板.
     *
     * @param dto 新增的模板信息
     * @return 新增后的模板信息
     */
    @PostMapping
    public Result<ScrumTemplateVO> save(@RequestBody @Validated(Create.class) ScrumTemplateDTO dto) {
        ScrumTemplate save = templateService.save(templateConvert.dto2entity(dto));
        return Result.createSuccess(templateConvert.entity2vo(save));
    }
    
    /**
     * 更新模板.
     *
     * @param dto 更新的模板信息
     * @return 更新后的模板信息
     */
    @PutMapping
    public Result<ScrumTemplateVO> update(@RequestBody @Validated(Update.class) ScrumTemplateDTO dto) {
        ScrumTemplate update = templateService.update(templateConvert.dto2entity(dto));
        return Result.updateSuccess(templateConvert.entity2vo(update));
    }
    
    /**
     * 删除模板.
     *
     * @param id 删除的模板ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public Result<Object> delete(@PathVariable Long id) {
        templateService.deleteById(id);
        return Result.deleteSuccess();
    }
    
    /**
     * 根据模板名称查询启用的模板信息.
     *
     * @param name 模板名称
     * @return 查询结果
     */
    @GetMapping("/list-enabled-by-name")
    public Result<List<ScrumTemplateVO>> listEnabledByName(@RequestParam(required = false) String name) {
        List<ScrumTemplate> scrumTemplates = templateService.listEnabledByName(name, 9L);
        return Result.success(templateConvert.entities2vos(scrumTemplates));
    }
}
