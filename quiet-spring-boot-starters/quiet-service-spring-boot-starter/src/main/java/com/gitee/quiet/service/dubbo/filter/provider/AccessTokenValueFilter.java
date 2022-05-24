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
