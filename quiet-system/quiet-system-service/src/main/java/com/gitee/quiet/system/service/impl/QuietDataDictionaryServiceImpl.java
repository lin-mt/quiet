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
import com.gitee.quiet.system.entity.QuietDataDictionary;
import com.gitee.quiet.system.repository.QuietDataDictionaryRepository;
import com.gitee.quiet.system.service.QuietDataDictionaryService;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.gitee.quiet.system.entity.QQuietDataDictionary.quietDataDictionary;

/**
 * 数据字典Service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
public class QuietDataDictionaryServiceImpl implements QuietDataDictionaryService {
    
    private final JPAQueryFactory jpaQueryFactory;
    
    private final QuietDataDictionaryRepository dataDictionaryRepository;
    
    public QuietDataDictionaryServiceImpl(JPAQueryFactory jpaQueryFactory,
            QuietDataDictionaryRepository dataDictionaryRepository) {
        this.jpaQueryFactory = jpaQueryFactory;
        this.dataDictionaryRepository = dataDictionaryRepository;
    }
    
    @Override
    public List<QuietDataDictionary> treeByType(String type) {
        List<QuietDataDictionary> dataDictionaries;
        if (StringUtils.isNoneBlank(type)) {
            dataDictionaries = dataDictionaryRepository.findAllByType(type);
        } else {
            dataDictionaries = dataDictionaryRepository.findAll();
        }
        return buildTreeData(dataDictionaries);
    }
    
    @Override
    public QueryResults<QuietDataDictionary> page(QuietDataDictionary params, @NotNull Pageable page) {
        return SelectBuilder.booleanBuilder(params).from(jpaQueryFactory, quietDataDictionary, page);
    }
    
    @Override
    public QuietDataDictionary save(@NotNull QuietDataDictionary save) {
        checkDataDictionaryInfo(save);
        return dataDictionaryRepository.save(save);
    }
    
    @Override
    public void delete(@NotNull Long id) {
        Optional<QuietDataDictionary> delete = dataDictionaryRepository.findById(id);
        if (delete.isEmpty()) {
            throw new ServiceException("dataDictionary.not.exist");
        }
        List<QuietDataDictionary> children = dataDictionaryRepository.findAllByParentId(delete.get().getId());
        if (CollectionUtils.isNotEmpty(children)) {
            throw new ServiceException("dataDictionary.can.not.delete.has.children");
        }
        dataDictionaryRepository.deleteById(id);
    }
    
    @Override
    public QuietDataDictionary update(@NotNull QuietDataDictionary update) {
        checkDataDictionaryInfo(update);
        return dataDictionaryRepository.saveAndFlush(update);
    }
    
    @Override
    public List<QuietDataDictionary> treeByTypeForSelect(String type) {
        List<QuietDataDictionary> dataDictionaries = dataDictionaryRepository
                .findAllByTypeAndKeyIsNullAndParentIdIsNull(type);
        return buildTreeData(dataDictionaries);
    }
    
    private List<QuietDataDictionary> buildTreeData(List<QuietDataDictionary> dataDictionaries) {
        Map<Long, QuietDataDictionary> dataDictionaryToId = new HashMap<>(dataDictionaries.size());
        for (QuietDataDictionary department : dataDictionaries) {
            dataDictionaryToId.put(department.getId(), department);
        }
        List<QuietDataDictionary> result = new ArrayList<>();
        for (QuietDataDictionary department : dataDictionaries) {
            if (department.getParentId() == null) {
                result.add(department);
            } else {
                dataDictionaryToId.get(department.getParentId()).addChildren(department);
            }
        }
        return result;
    }
    
    private void checkDataDictionaryInfo(@NotNull QuietDataDictionary dataDictionary) {
        if (StringUtils.isBlank(dataDictionary.getKey())) {
            dataDictionary.setKey(null);
        }
        boolean sameNullState = (dataDictionary.getKey() != null && dataDictionary.getParentId() != null) || (
                dataDictionary.getKey() == null && dataDictionary.getParentId() == null);
        if (!sameNullState) {
            throw new ServiceException("dataDictionary.key.parentId.differentNullState");
        }
        QuietDataDictionary exist = dataDictionaryRepository
                .findByTypeAndKey(dataDictionary.getType(), dataDictionary.getKey());
        if (exist != null && !exist.getId().equals(dataDictionary.getId())) {
            throw new ServiceException("dataDictionary.type.key.exist", dataDictionary.getType(),
                    dataDictionary.getKey());
        }
        if (dataDictionary.getParentId() != null) {
            if (!dataDictionaryRepository.existsById(dataDictionary.getParentId())) {
                throw new ServiceException("dataDictionary.parentId.not.exist", dataDictionary.getParentId());
            }
        }
    }
}
