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

package com.gitee.quiet.system.controller;

import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.system.convert.QuietHolidayConvert;
import com.gitee.quiet.system.dto.QuietHolidayDTO;
import com.gitee.quiet.system.entity.QuietHoliday;
import com.gitee.quiet.system.service.QuietHolidayService;
import com.gitee.quiet.system.vo.QuietHolidayVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 假期接口.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@RestController
@AllArgsConstructor
@RequestMapping("/holiday")
public class QuietHolidayController {

  private final QuietHolidayService service;

  private final QuietHolidayConvert convert;

  @GetMapping("/year/{year}")
  public Result<List<QuietHolidayVO>> listByYear(@PathVariable Integer year) {
    List<QuietHoliday> holidays = service.listAllByYear(year);
    return Result.success(convert.entities2vos(holidays));
  }

  @PutMapping
  public Result<QuietHolidayVO> updateHoliday(@RequestBody QuietHolidayDTO req) {
    QuietHoliday update = service.update(convert.dto2entity(req));
    return Result.updateSuccess(convert.entity2vo(update));
  }
}
