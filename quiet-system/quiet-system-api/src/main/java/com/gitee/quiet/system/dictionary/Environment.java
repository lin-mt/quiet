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

package com.gitee.quiet.system.dictionary;

import com.gitee.quiet.jpa.entity.Dictionary;

/**
 * 环境数据字典.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class Environment extends Dictionary<Environment> {

    /**
     * 开发环境
     */
    public static final Environment Develop = new Environment("Develop");

    /**
     * 测试环境
     */
    public static final Environment Test = new Environment("Test");

    /**
     * 生产环境
     */
    public static final Environment Produce = new Environment("Produce");

    private Environment(String key) {
        super(Environment.class.getSimpleName(), key);
    }
}
