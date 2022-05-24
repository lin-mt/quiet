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

package com.gitee.quiet.service.dubbo.filter.consumer;

import com.gitee.quiet.service.dubbo.filter.DubboThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.Filter;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcException;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 消费者在 rpc 上下文添加 tokenValue.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Activate(group = CommonConstants.CONSUMER)
public class AccessTokenValueFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        String tokenValue;
        if (requestAttributes != null) {
            tokenValue = (String) ((ServletRequestAttributes) requestAttributes).getRequest()
                .getAttribute(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE);
        } else {
            tokenValue = DubboThreadLocal.USER_TOKEN.get();
        }
        if (StringUtils.isNotBlank(tokenValue)) {
            invocation.setAttachment(OAuth2AuthenticationDetails.ACCESS_TOKEN_VALUE, tokenValue);
        }
        return invoker.invoke(invocation);
    }
}
