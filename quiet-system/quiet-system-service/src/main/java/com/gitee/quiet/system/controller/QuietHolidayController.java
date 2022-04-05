package com.gitee.quiet.system.controller;

import com.gitee.quiet.service.result.Result;
import com.gitee.quiet.system.convert.QuietHolidayConvert;
import com.gitee.quiet.system.dto.QuietHolidayDTO;
import com.gitee.quiet.system.entity.QuietHoliday;
import com.gitee.quiet.system.service.QuietHolidayService;
import com.gitee.quiet.system.vo.QuietHolidayVO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
