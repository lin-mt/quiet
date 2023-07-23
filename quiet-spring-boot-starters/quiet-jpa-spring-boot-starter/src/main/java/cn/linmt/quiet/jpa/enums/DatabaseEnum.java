package cn.linmt.quiet.jpa.enums;

import java.io.Serializable;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public interface DatabaseEnum<T extends Serializable> {

  /** 数据库字段至枚举的转换工具。 */
  static <V extends Serializable, E extends Enum<E> & DatabaseEnum<V>> E valueToEnum(
      Class<E> enumType, V value) {
    return Stream.of(enumType.getEnumConstants())
        .filter(item -> Objects.equals(item.getValue(), value))
        .findFirst()
        .orElseThrow(
            () ->
                new IllegalArgumentException(
                    "Unknown enum value: " + value + " for type: " + enumType.getSimpleName()));
  }

  /**
   * 存储数据库的值
   *
   * @return 数据库值
   */
  T getValue();
}
