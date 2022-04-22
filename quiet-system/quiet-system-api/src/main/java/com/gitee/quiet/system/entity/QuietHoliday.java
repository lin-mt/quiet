package com.gitee.quiet.system.entity;

import com.gitee.quiet.jpa.entity.base.BaseEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * 假期.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "quiet_holiday")
public class QuietHoliday extends BaseEntity {

    /**
     * 当前日期
     */
    @Column(name = "date_info", nullable = false)
    private LocalDate dateInfo;

    /**
     * 是否是假期
     */
    @Column(name = "is_holiday", columnDefinition = "TINYINT(1)", nullable = false)
    private Boolean isHoliday;

}
