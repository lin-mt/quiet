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

package com.gitee.quiet.service.dubbo.filter.provider;

import com.gitee.quiet.service.dubbo.filter.DubboThreadLocal;
import com.gitee.quiet.service.security.context.QuietSecurityContext;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

/**
 * 提供者从 rpc 上下文获取 tokenValue.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Activate(group = CommonConstants.PROVIDER)
public class AccessTokenValueFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String tokenValue = invocation.getAttachment(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE);
        if (StringUtils.isNotBlank(tokenValue)) {
            DubboThreadLocal.USER_TOKEN.set(tokenValue);
            SecurityContextHolder.setContext(new QuietSecurityContext(tokenValue));
        }
        try {
            return invoker.invoke(invocation);
        } finally {
            if (StringUtils.isNotBlank(tokenValue)) {
                SecurityContextHolder.clearContext();
            }
        }
    }
}
