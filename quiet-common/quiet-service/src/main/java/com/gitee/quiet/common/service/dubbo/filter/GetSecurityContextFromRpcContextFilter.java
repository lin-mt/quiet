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

package com.gitee.quiet.common.service.dubbo.filter;

import com.gitee.quiet.common.service.constant.DubboConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;

/**
 * 提供者从 rpc 上下文获取 SecurityContext.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Activate(group = DubboConstants.PROVIDER)
public class GetSecurityContextFromRpcContextFilter implements Filter {
    
    private static final JdkSerializationStrategy SERIALIZATION_STRATEGY = new JdkSerializationStrategy();
    
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        Object securityContextAttachment = invocation.getObjectAttachment(DubboConstants.SECURITY_CONTEXT);
        if (securityContextAttachment instanceof byte[]) {
            byte[] securityContextByteArray = (byte[]) securityContextAttachment;
            SecurityContext securityContext = SERIALIZATION_STRATEGY
                    .deserialize(securityContextByteArray, SecurityContext.class);
            SecurityContextHolder.setContext(securityContext);
        }
        try {
            return invoker.invoke(invocation);
        } finally {
            if (securityContextAttachment instanceof byte[]) {
                SecurityContextHolder.clearContext();
            }
        }
    }
}
