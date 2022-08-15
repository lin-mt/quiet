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

package com.gitee.quiet.service.dubbo.filter.consumer;

import com.gitee.quiet.service.dubbo.filter.DubboThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
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
      tokenValue =
          (String)
              ((ServletRequestAttributes) requestAttributes)
                  .getRequest()
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
