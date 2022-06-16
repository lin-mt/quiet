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

package com.gitee.quiet.doc.controller;

import java.io.IOException;
import java.util.Objects;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 请求代理.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Controller
@RequestMapping("/proxy")
public class ProxyController {

    private final OkHttpClient client = new OkHttpClient();

    @GetMapping("/swagger")
    public String swagger(@RequestParam("path") String path) throws IOException {
        try (Response response = client.newCall(new Request.Builder().url(path).get().build()).execute()) {
            return Objects.requireNonNull(response.body()).string();
        }
    }
}
