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

package com.gitee.quiet.system.service;

import com.gitee.quiet.system.entity.QuietHoliday;
import java.util.List;

/**
 * 假期service.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface QuietHolidayService {

    /**
     * 根据年份查询假期信息
     *
     * @param year 年份
     * @return 该年份的假期信息
     */
    List<QuietHoliday> listAllByYear(Integer year);

    /**
     * 更新假期信息
     *
     * @param entity 假期信息
     * @return 假期信息
     */
    QuietHoliday update(QuietHoliday entity);
}
