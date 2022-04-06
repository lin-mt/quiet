package com.gitee.quiet.system.vo;

import com.gitee.quiet.service.vo.BaseVO;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * 假期VO.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
public class QuietHolidayVO extends BaseVO {
    
    /**
     * 当前日期
     */
    private LocalDate dateInfo;
    
    /**
     * 是否是假期
     */
    private Boolean isHoliday;
}
