package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.common.service.exception.ServiceException;
import com.gitee.quiet.common.service.util.Where;
import com.gitee.quiet.system.entity.QuietDataDictionary;
import com.gitee.quiet.system.repository.QuietDataDictionaryRepository;
import com.gitee.quiet.system.service.QuietDataDictionaryService;
import com.querydsl.core.BooleanBuilder;
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
        List<QuietDataDictionary> dictionaries;
        if (StringUtils.isNoneBlank(type)) {
            dictionaries = dataDictionaryRepository.findAllByType(type);
        } else {
            dictionaries = dataDictionaryRepository.findAll();
        }
        Map<Long, QuietDataDictionary> dataDictionaryToId = new HashMap<>(dictionaries.size());
        for (QuietDataDictionary department : dictionaries) {
            dataDictionaryToId.put(department.getId(), department);
        }
        List<QuietDataDictionary> result = new ArrayList<>();
        for (QuietDataDictionary department : dictionaries) {
            if (department.getParentId() == null) {
                result.add(department);
            } else {
                dataDictionaryToId.get(department.getParentId()).addChildren(department);
            }
        }
        return result;
    }
    
    @Override
    public QueryResults<QuietDataDictionary> page(QuietDataDictionary params, @NotNull Pageable page) {
        BooleanBuilder builder = new BooleanBuilder();
        if (params != null) {
            Where.notNullEq(params.getId(), quietDataDictionary.id, builder);
            Where.notBlankContains(params.getType(), quietDataDictionary.type, builder);
            Where.notBlankContains(params.getKey(), quietDataDictionary.key, builder);
            Where.notBlankContains(params.getRemark(), quietDataDictionary.remark, builder);
            Where.notNullEq(params.getParentId(), quietDataDictionary.parentId, builder);
        }
        return jpaQueryFactory.selectFrom(quietDataDictionary).where(builder).offset(page.getOffset())
                .limit(page.getPageSize()).fetchResults();
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
        List<QuietDataDictionary> children = dataDictionaryRepository.findAllByParentId(delete.get().getParentId());
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
    
    private void checkDataDictionaryInfo(@NotNull QuietDataDictionary dataDictionary) {
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
