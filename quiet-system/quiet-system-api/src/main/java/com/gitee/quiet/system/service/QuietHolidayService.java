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
