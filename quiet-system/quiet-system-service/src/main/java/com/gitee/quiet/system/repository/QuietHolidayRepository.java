package com.gitee.quiet.system.repository;

import com.gitee.quiet.jpa.repository.QuietRepository;
import com.gitee.quiet.system.entity.QuietHoliday;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * 假期 repository.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Repository
public interface QuietHolidayRepository extends QuietRepository<QuietHoliday> {

    @Query(value = "select * from quiet_holiday where date_format(current_date, '%Y') = :year", nativeQuery = true)
    List<QuietHoliday> findAllByYear(@Param("year") Integer year);
}
