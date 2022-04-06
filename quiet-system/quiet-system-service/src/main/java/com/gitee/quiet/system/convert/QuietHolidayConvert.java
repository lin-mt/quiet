package com.gitee.quiet.system.convert;

import com.gitee.quiet.service.dto.QuietConvert;
import com.gitee.quiet.system.dto.QuietHolidayDTO;
import com.gitee.quiet.system.entity.QuietHoliday;
import com.gitee.quiet.system.vo.QuietHolidayVO;
import org.mapstruct.Mapper;

/**
 * 假期信息转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Mapper
public interface QuietHolidayConvert extends QuietConvert<QuietHoliday, QuietHolidayDTO, QuietHolidayVO> {

}
