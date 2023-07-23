package cn.linmt.quiet.jpa.entity;

import cn.linmt.quiet.jpa.id.Generator;
import cn.linmt.quiet.validation.group.Create;
import cn.linmt.quiet.validation.group.Update;
import com.querydsl.core.BooleanBuilder;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
public class QuietEntity implements Serializable {

  @Id
  @Null(groups = Create.class)
  @NotNull(groups = Update.class)
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "IdGenerator")
  @GenericGenerator(name = "IdGenerator", type = Generator.class)
  private Long id;

  @CreatedBy
  @Column(name = "creator", updatable = false)
  private Long creator;

  @LastModifiedBy
  @Column(name = "updater", insertable = false)
  private Long updater;

  @CreatedDate
  @Column(name = "gmt_create", updatable = false)
  private LocalDateTime gmtCreate;

  @LastModifiedDate
  @Column(name = "gmt_update", insertable = false)
  private LocalDateTime gmtUpdate;

  @Nullable
  public BooleanBuilder booleanBuilder() {
    return null;
  }
}
