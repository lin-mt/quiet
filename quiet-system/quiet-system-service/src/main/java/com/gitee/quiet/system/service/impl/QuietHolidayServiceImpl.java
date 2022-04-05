package com.gitee.quiet.system.service.impl;

import com.gitee.quiet.system.entity.QuietHoliday;
import com.gitee.quiet.system.repository.QuietHolidayRepository;
import com.gitee.quiet.system.service.QuietHolidayService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 假期service实现类.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Service
@AllArgsConstructor
public class QuietHolidayServiceImpl implements QuietHolidayService {
    
    private final QuietHolidayRepository repository;
    
    @Override
    public List<QuietHoliday> listAllByYear(Integer year) {
        List<QuietHoliday> holidays = repository.findAllByYear(year);
        if (CollectionUtils.isEmpty(holidays)) {
            LocalDate date = LocalDate.of(year, 1, 1);
            List<QuietHoliday> addHoliday = new ArrayList<>();
            while (date.getYear() == year) {
                QuietHoliday holiday = new QuietHoliday();
                holiday.setIsHoliday(
                        DayOfWeek.SATURDAY.equals(date.getDayOfWeek()) || DayOfWeek.SUNDAY.equals(date.getDayOfWeek()));
                holiday.setDateInfo(LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth()));
                addHoliday.add(holiday);
                date = date.plusDays(1L);
            }
            holidays = repository.saveAllAndFlush(addHoliday);
        }
        return holidays;
    }
    
    @Override
    public QuietHoliday update(QuietHoliday entity) {
        return repository.saveAndFlush(entity);
    }
}
