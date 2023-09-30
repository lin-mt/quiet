package cn.linmt.quiet.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@MappedSuperclass
public class Hierarchy extends QuietEntity implements cn.linmt.quiet.common.type.Hierarchy<Long> {

  /** 父级ID */
  @Column(name = "parent_id")
  private Long parentId;
}
