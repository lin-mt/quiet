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

package com.gitee.quiet.scrum.repository;

import com.gitee.quiet.scrum.entity.ScrumTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 模板Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface ScrumTemplateRepository extends JpaRepository<ScrumTemplate, Long> {
    
    /**
     * 查询启用的或者创建者为 creator 的模板信息
     *
     * @param enable  是否启用
     * @param creator 创建者ID
     * @return 是否启用为enable或者创建者为creator创建模板信息
     */
    List<ScrumTemplate> findAllByEnableOrCreator(boolean enable, Long creator);
    
    /**
     * 根据模板名称查找模板信息
     *
     * @param name 模板名称
     * @return 模板信息
     */
    ScrumTemplate findByName(String name);
}
