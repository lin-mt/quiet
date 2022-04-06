package com.gitee.quiet.system.dto;

import com.gitee.quiet.service.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 假期 DTO.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietHolidayDTO extends BaseDTO {
    
    /**
     * 当前日期
     */
    private LocalDate dateInfo;
    
    /**
     * 是否是假期
     */
    private Boolean isHoliday;
}
