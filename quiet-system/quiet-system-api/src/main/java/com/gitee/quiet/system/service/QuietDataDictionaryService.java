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

package com.gitee.quiet.system.service;

import com.gitee.quiet.system.entity.QuietDataDictionary;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 数据字典Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietDataDictionaryService {
    
    /**
     * 根据数据字典类型返回该类型的树形结构
     *
     * @param type 数据字典类型
     * @return type的树形结构
     */
    List<QuietDataDictionary> treeByType(String type);
    
    /**
     * 分页查询数据字典.
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 查询的所有信息
     */
    QueryResults<QuietDataDictionary> page(QuietDataDictionary params, Pageable page);
    
    /**
     * 新增数据字典.
     *
     * @param save 新增的数据字典信息
     * @return 新增后的数据字典信息
     */
    QuietDataDictionary save(QuietDataDictionary save);
    
    /**
     * 根据数据字典ID删除数据
     *
     * @param id 要删除的数据字典ID
     */
    void delete(Long id);
    
    /**
     * 更新数据字典
     *
     * @param update 要更新的数据字典
     * @return 更新后的数据字典信息
     */
    QuietDataDictionary update(QuietDataDictionary update);
    
    /**
     * 查询数据字典提供选项
     *
     * @param type 数据字典类型
     * @return 该数据字典类型下的选项
     */
    List<QuietDataDictionary> treeByTypeForSelect(String type);
}
