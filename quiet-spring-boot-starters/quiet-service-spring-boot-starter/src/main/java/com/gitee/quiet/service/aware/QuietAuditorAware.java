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

package com.gitee.quiet.service.aware;

import com.gitee.quiet.jpa.entity.base.BaseEntity;
import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * JpaAuditorAware.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class QuietAuditorAware implements AuditorAware<Long> {

    /**
     * @return 操作者的 ID
     */
    @NonNull
    @Override
    public Optional<Long> getCurrentAuditor() {
        // 获取创建者和更新者信息
        // @formatter:off
        Optional<Object> principle = Optional.ofNullable(SecurityContextHolder.getContext())
            .map(SecurityContext::getAuthentication).filter(Authentication::isAuthenticated)
            .map(Authentication::getPrincipal);
        // @formatter:on
        Long id = null;
        if (principle.isPresent()) {
            Object currentUser = principle.get();
            if (currentUser instanceof BaseEntity) {
                id = ((BaseEntity) currentUser).getId();
            }
        }
        return Optional.ofNullable(id);
    }

}
