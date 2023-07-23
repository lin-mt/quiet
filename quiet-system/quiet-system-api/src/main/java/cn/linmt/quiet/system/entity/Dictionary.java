package cn.linmt.quiet.system.entity;

import cn.linmt.quiet.common.type.Dict;
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
@Table(name = "system_dictionary")
public class Dictionary extends QuietEntity implements Dict {

  /** 数据字典类型 */
  @ManyToOne
  @JoinColumn(name = "type_id", nullable = false)
  private DictionaryType type;

  /** 父级数据字典 */
  @ManyToOne
  @JoinColumn(name = "parent_id")
  private Dictionary parent;

  /** 字典key，格式为每层级占两位数字，第一层级范围：00-99，第二层级的前两位为第一层级的key， 所以第二层级范围为：0000-9999，后续层级以此类推 */
  @Length(max = 18)
  @Column(name = "dict_key", length = 18, nullable = false)
  private String key;

  /** 子数据字典 */
  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  private List<Dictionary> children = new ArrayList<>();

  /** 名称 */
  @Length(max = 10)
  @Column(name = "dict_name", length = 10, nullable = false)
  private String name;

  /** 是否启用 */
  @Column(name = "enabled", columnDefinition = "TINYINT(1)", nullable = false)
  private boolean enabled;
}
