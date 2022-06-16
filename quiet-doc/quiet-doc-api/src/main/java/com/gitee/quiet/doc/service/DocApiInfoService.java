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

package com.gitee.quiet.doc.service;

import com.gitee.quiet.doc.entity.DocApiInfo;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * api 信息 Service.
 *
 * @author @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DocApiInfoService {

    /**
     * 新增api信息
     *
     * @param save 新增的api信息
     * @return 新增的api数据库信息
     */
    DocApiInfo save(DocApiInfo save);

    /**
     * 更新api信息
     *
     * @param update 要更新的api信息
     * @return 更新后的api信息
     */
    DocApiInfo update(DocApiInfo update);

    /**
     * 根据apiId查询api信息
     *
     * @param apiId apiId
     * @return api信息
     */
    DocApiInfo getByApiId(Long apiId);

    /**
     * 根据 api ID 批量查询api信息
     *
     * @param apiIds api id 集合
     * @return api 信息
     */
    List<DocApiInfo> listByApiIds(Set<Long> apiIds);

    /**
     * 批量保存
     *
     * @param apiInfos api 详细信息
     */
    void saveAll(Collection<DocApiInfo> apiInfos);
}
