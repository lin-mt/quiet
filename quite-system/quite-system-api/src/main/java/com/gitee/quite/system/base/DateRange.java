package com.gitee.quite.system.base;

import java.time.LocalDateTime;

/**
 * 时间范围的查询参数.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public class DateRange {
    
    private LocalDateTime start;
    
    private LocalDateTime end;
    
    public LocalDateTime getStart() {
        return start;
    }
    
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    
    public LocalDateTime getEnd() {
        return end;
    }
    
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }
}