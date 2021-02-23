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

package com.gitee.quiet.system.repository;

import com.gitee.quiet.system.entity.QuietDataDictionary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据字典Repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietDataDictionaryRepository extends JpaRepository<QuietDataDictionary, Long> {
    
    /**
     * 根据数据字典类型查询数据字典信息
     *
     * @param type 要查询的数据字典类型
     * @return 该类型的所有数据字典信息
     */
    List<QuietDataDictionary> findAllByType(String type);
    
    /**
     * 根据数据字典类型查询数据字典信息，不包含一级数据字典
     *
     * @param type 要查询的数据字典类型
     * @return 该类型下的所有数据字典信息
     */
    List<QuietDataDictionary> findAllByTypeAndKeyIsNullAndParentIdIsNull(String type);
    
    /**
     * 根据父数据字典ID查询子数据字典信息
     *
     * @param parentId 父数据字典ID
     * @return 所有子数据字典
     */
    List<QuietDataDictionary> findAllByParentId(Long parentId);
    
    /**
     * 根据数据字典类型和key查询数据字典
     *
     * @param type 数据字典类型
     * @param key  数据字典key
     * @return 数据字典信息
     */
    QuietDataDictionary findByTypeAndKey(String type, String key);
}
