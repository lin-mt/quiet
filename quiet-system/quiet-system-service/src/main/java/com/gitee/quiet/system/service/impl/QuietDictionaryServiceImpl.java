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

import com.gitee.quiet.jpa.utils.EntityUtils;
import com.gitee.quiet.jpa.utils.SelectBuilder;
import com.gitee.quiet.service.exception.ServiceException;
import com.gitee.quiet.system.entity.QuietDictionary;
import com.gitee.quiet.system.repository.QuietDictionaryRepository;
import com.gitee.quiet.system.service.QuietDictionaryService;
import com.querydsl.core.BooleanBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;


/**
 * 数据字典Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietDictionaryServiceImpl implements QuietDictionaryService {
    
    public static final String CACHE_NAME = "quiet:system:dictionary";
    
    private final QuietDictionaryRepository dictionaryRepository;
    
    public QuietDictionaryServiceImpl(QuietDictionaryRepository dictionaryRepository) {
        this.dictionaryRepository = dictionaryRepository;
    }
    
    @Override
    public List<QuietDictionary> treeByType(String type) {
        List<QuietDictionary> dictionaries;
        if (StringUtils.isNoneBlank(type)) {
            dictionaries = dictionaryRepository.findAllByType(type);
        } else {
            dictionaries = dictionaryRepository.findAll();
        }
        return EntityUtils.buildTreeData(dictionaries);
    }
    
    @Override
    public Page<QuietDictionary> page(QuietDictionary params, @NotNull Pageable page) {
        BooleanBuilder predicate = SelectBuilder.booleanBuilder(params).getPredicate();
        return dictionaryRepository.findAll(predicate, page);
    }
    
    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#save.type")
    public QuietDictionary save(@NotNull QuietDictionary save) {
        checkDictionaryInfo(save);
        return dictionaryRepository.save(save);
    }
    
    @Override
    @CacheEvict(cacheNames = CACHE_NAME, key = "#result.type")
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
    @CacheEvict(cacheNames = CACHE_NAME, key = "#update.type")
    public QuietDictionary update(@NotNull QuietDictionary update) {
        checkDictionaryInfo(update);
        return dictionaryRepository.saveAndFlush(update);
    }
    
    @Override
    @Cacheable(cacheNames = CACHE_NAME, key = "#type", condition = "#type != null ", sync = true)
    public List<QuietDictionary> listByTypeForSelect(String type) {
        if (StringUtils.isBlank(type)) {
            return List.of();
        }
        List<QuietDictionary> dictionaries = dictionaryRepository.findAllByTypeAndKeyIsNotNullAndParentIdIsNotNull(
                type);
        return EntityUtils.buildTreeData(dictionaries);
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
