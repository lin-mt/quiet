package com.gitee.quiet.system.vo;

import com.gitee.quiet.service.vo.BaseVO;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

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
