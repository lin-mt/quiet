package cn.linmt.quiet.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@MappedSuperclass
public class Sortable extends QuietEntity implements cn.linmt.quiet.common.type.Sortable {

  /** 序号 */
  @Min(0)
  @Column(name = "serial_num", nullable = false)
  private int serialNum = 0;
}
