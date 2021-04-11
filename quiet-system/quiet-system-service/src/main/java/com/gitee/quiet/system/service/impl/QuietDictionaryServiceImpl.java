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

package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.common.service.jpa.SelectBuilder;
import com.gitee.quiet.system.entity.QuietDictionary;
import com.gitee.quiet.system.repository.QuietDictionaryRepository;
import com.gitee.quiet.system.service.QuietDictionaryService;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.gitee.quiet.system.entity.QQuietDictionary.quietDictionary;


/**
 * 数据字典Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietDictionaryServiceImpl implements QuietDictionaryService {
    
    private static final String CACHE_INFO = "quiet:system:dictionary";
    
    private static final String TREE_CACHE_INFO = CACHE_INFO + ":tree";
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuietDictionaryRepository dictionaryRepository;
    
    public QuietDictionaryServiceImpl(JPAQueryFactory jpaQueryFactory, QuietDictionaryRepository dictionaryRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.dictionaryRepository = dictionaryRepository;
    }
    
    @Override
    @Cacheable(value = TREE_CACHE_INFO, key = "T(String).valueOf(#type)")
    public List<QuietDictionary> treeByType(String type) {
        List<QuietDictionary> dictionaries;
        if (StringUtils.isNoneBlank(type)) {
            dictionaries = dictionaryRepository.findAllByType(type);
        } else {
            dictionaries = dictionaryRepository.findAll();
        }
        return buildTreeData(dictionaries);
    }
    
    @Override
    public QueryResults<QuietDictionary> page(QuietDictionary params, @NotNull Pageable page) {
        return SelectBuilder.booleanBuilder(params).from(jpaQueryFactory, quietDictionary, page);
    }
    
    @Override
    @CacheEvict(value = {CACHE_INFO, TREE_CACHE_INFO}, allEntries = true)
    public QuietDictionary save(@NotNull QuietDictionary save) {
        checkDictionaryInfo(save);
        return dictionaryRepository.save(save);
    }
    
    @Override
    @CacheEvict(value = {CACHE_INFO, TREE_CACHE_INFO}, allEntries = true)
    public QuietDictionary delete(@NotNull Long id) {
        Optional<QuietDictionary> delete = dictionaryRepository.findById(id);
        if (delete.isEmpty()) {
            throw new ServiceException("dictionary.not.exist");
        }
        List<QuietDictionary> children = dictionaryRepository.findAllByParentId(delete.get().getId());
        if (CollectionUtils.isNotEmpty(children)) {
            throw new ServiceException("dictionary.can.not.delete.has.children");
        }
        dictionaryRepository.deleteById(id);
        return delete.get();
    }
    
    @Override
    @CacheEvict(value = {CACHE_INFO, TREE_CACHE_INFO}, allEntries = true)
    public QuietDictionary update(@NotNull QuietDictionary update) {
        checkDictionaryInfo(update);
        return dictionaryRepository.saveAndFlush(update);
    }
    
    @Override
    @Cacheable(value = TREE_CACHE_INFO, key = "#type", condition = "#type != null")
    public List<QuietDictionary> treeByTypeForSelect(String type) {
        if (StringUtils.isBlank(type)) {
            return List.of();
        }
        List<QuietDictionary> dictionaries = dictionaryRepository.findAllByTypeAndKeyIsNullAndParentIdIsNull(type);
        return buildTreeData(dictionaries);
    }
    
    private List<QuietDictionary> buildTreeData(List<QuietDictionary> dictionaries) {
        Map<Long, QuietDictionary> dictionaryToId = new HashMap<>(dictionaries.size());
        for (QuietDictionary department : dictionaries) {
            dictionaryToId.put(department.getId(), department);
        }
        List<QuietDictionary> result = new ArrayList<>();
        for (QuietDictionary department : dictionaries) {
            if (department.getParentId() == null) {
                result.add(department);
            } else {
                dictionaryToId.get(department.getParentId()).addChildren(department);
            }
        }
        return result;
    }
    
    private void checkDictionaryInfo(@NotNull QuietDictionary dictionary) {
        if (StringUtils.isBlank(dictionary.getKey())) {
            dictionary.setKey(null);
        }
        boolean sameNullState =
                (dictionary.getKey() != null && dictionary.getParentId() != null) || (dictionary.getKey() == null
                        && dictionary.getParentId() == null);
        if (!sameNullState) {
            throw new ServiceException("dictionary.key.parentId.differentNullState");
        }
        if (dictionary.getParentId() != null) {
            // 有父数据字典ID，将当前的数据字典 type 设置为父数据字典 type
            Optional<QuietDictionary> parent = dictionaryRepository.findById(dictionary.getParentId());
            if (parent.isEmpty()) {
                throw new ServiceException("dictionary.parentId.not.exist", dictionary.getParentId());
            }
            dictionary.setType(parent.get().getType());
        } else {
            // 没有父数据字典ID，当前的数据字典 type 必填
            if (StringUtils.isBlank(dictionary.getType())) {
                throw new ServiceException("dictionary.type.required");
            }
        }
        QuietDictionary exist = dictionaryRepository.findByTypeAndKey(dictionary.getType(), dictionary.getKey());
        if (exist != null && !exist.getId().equals(dictionary.getId())) {
            throw new ServiceException("dictionary.type.key.exist", dictionary.getType(), dictionary.getKey());
        }
    }
}