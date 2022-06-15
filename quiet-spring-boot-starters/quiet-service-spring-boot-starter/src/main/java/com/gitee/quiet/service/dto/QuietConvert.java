/*
 * Copyright (C) 2022  lin-mt<lin-mt@outlook.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.gitee.quiet.service.dto;

import com.gitee.quiet.jpa.entity.base.BaseEntity;
import com.gitee.quiet.service.vo.BaseVO;
import com.querydsl.core.QueryResults;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Sets;
import org.springframework.data.domain.Page;

/**
 * 实体转换接口.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietConvert<E extends BaseEntity, D extends BaseDTO, V extends BaseVO> {

    /**
     * DTO 转实体
     *
     * @param dto 转换的DTO
     * @return 实体信息
     */
    E dto2entity(D dto);

    /**
     * 实体转VO
     *
     * @param entity 实体信息
     * @return Vo信息
     */
    V entity2vo(E entity);

    /**
     * 实体分页信息转VO分页信息
     *
     * @param ePage 实体分页信息
     * @return VO 分页信息
     */
    default Page<V> page2page(Page<E> ePage) {
        return ePage.map(this::entity2vo);
    }

    /**
     * queryDsl 实体分页查询结果转vo分页查询结果
     *
     * @param results 实体分页信息
     * @return vo分页信息
     */
    default QueryResults<V> results2results(QueryResults<E> results) {
        return new QueryResults<>(this.entities2vos(results.getResults()), results.getLimit(), results.getOffset(),
            results.getTotal());
    }

    /**
     * List 实体信息转 vo List
     *
     * @param entities 实体信息
     * @return vo信息
     */
    default List<V> entities2vos(List<E> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Lists.newArrayList();
        }
        return entities.stream().map(this::entity2vo).collect(Collectors.toList());
    }

    /**
     * Set 实体信息转 VO Set
     *
     * @param entities 实体信息
     * @return vo 信息
     */
    default Set<V> entities2vos(Set<E> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Sets.newHashSet();
        }
        return entities.stream().map(this::entity2vo).collect(Collectors.toSet());
    }
}
