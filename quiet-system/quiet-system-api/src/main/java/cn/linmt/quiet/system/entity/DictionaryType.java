package cn.linmt.quiet.system.entity;

import cn.linmt.quiet.jpa.entity.QuietEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
@Setter
@Entity
@Table(name = "system_dictionary_type")
public class DictionaryType extends QuietEntity {

  /** 服务ID */
  @Length(max = 30)
  @Column(name = "service_id", length = 30)
  private String serviceId;

  /** key */
  @Length(max = 30)
  @Column(name = "type_key", length = 30)
  private String key;

  /** 名称 */
  @Length(max = 10)
  @Column(name = "type_name", length = 10)
  private String name;

  /** 是否启用 */
  @Column(name = "enabled", columnDefinition = "TINYINT(1)")
  private boolean enabled;

  /** 备注 */
  @Length(max = 100)
  @Column(name = "remark", length = 100)
  private String remark;

  /** 数据字典 */
  @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Dictionary> dictionaries = new ArrayList<>();
}
