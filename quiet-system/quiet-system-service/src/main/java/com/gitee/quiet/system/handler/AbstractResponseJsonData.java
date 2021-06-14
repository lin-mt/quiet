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

package com.gitee.quiet.system.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.common.base.result.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 返回 json 数据.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public abstract class AbstractResponseJsonData {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    /**
     * 返回 json 格式数据.
     *
     * @param response HttpServletResponse
     * @param result   result
     * @param <T>      数据类型
     * @throws IOException 数据异常
     */
    protected <T> void responseJsonData(HttpServletResponse response, Result<T> result) throws IOException {
        // 这里要设置 response 的 ContentType，否则返回中文会乱码
        // Spring 提供的 MediaType.APPLICATION_JSON_UTF8_VALUE 标注了过时，那就自己写个吧！
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(new ObjectMapper().writeValueAsString(result));
        out.flush();
        out.close();
    }
}
