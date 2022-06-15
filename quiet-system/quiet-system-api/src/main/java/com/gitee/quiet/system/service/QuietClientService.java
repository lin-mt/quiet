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

package com.gitee.quiet.system.service;

import com.gitee.quiet.system.entity.QuietClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.ClientDetailsService;

/**
 * 客户端Service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@SuppressWarnings("deprecation")
public interface QuietClientService extends ClientDetailsService {

    /**
     * 分页查询客户端信息
     *
     * @param params 查询参数
     * @param page   分页参数
     * @return 客户端信息
     */
    Page<QuietClient> page(QuietClient params, Pageable page);

    /**
     * 新增客户端信息
     *
     * @param save 要新增的客户端信息
     * @return 新增后的客户端信息
     */
    QuietClient save(QuietClient save);

    /**
     * 删除客户端信息
     *
     * @param id 要删除的客户端的ID
     */
    void deleteClientById(Long id);

    /**
     * 更新客户端信息
     *
     * @param update 要更新的客户端信息
     * @return 更新后的客户端信息
     */
    QuietClient update(QuietClient update);

    /**
     * 移除客户端的授权范围
     *
     * @param id    客户端的ID
     * @param scope 操作的授权范围
     */
    QuietClient removeClientScope(Long id, String scope);

    /**
     * 移除客户端的授权类型
     *
     * @param id                  客户端的ID
     * @param authorizedGrantType 操作的授权类型
     */
    QuietClient removeClientAuthorizedGrantType(Long id, String authorizedGrantType);
}
