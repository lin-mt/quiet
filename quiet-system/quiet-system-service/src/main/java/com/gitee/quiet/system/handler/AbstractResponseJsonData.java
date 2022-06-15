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

package com.gitee.quiet.system.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.quiet.service.result.Result;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
