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

package com.gitee.quiet.jpa.utils;

import com.gitee.quiet.common.core.entity.Parent;
import com.gitee.quiet.jpa.entity.base.BaseEntity;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 实体类工具.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class EntityUtils {
    
    private EntityUtils() {
    }
    
    /**
     * 构建树形结构的数据
     *
     * @param source 要构建的原数据
     * @param <T>    实体
     * @return 构建后的数据
     */
    public static <T extends BaseEntity & Parent<T>> List<T> buildTreeData(List<T> source) {
        List<T> treeData = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(source)) {
            Map<Long, T> idToEntity = source.stream().collect(Collectors.toMap(BaseEntity::getId, p -> p));
            Set<Long> keys = idToEntity.keySet();
            for (T datum : source) {
                if (datum.getParentId() == null || !keys.contains(datum.getParentId())) {
                    treeData.add(datum);
                    continue;
                }
                idToEntity.get(datum.getParentId()).addChildren(datum);
            }
        }
        return treeData;
    }
}
