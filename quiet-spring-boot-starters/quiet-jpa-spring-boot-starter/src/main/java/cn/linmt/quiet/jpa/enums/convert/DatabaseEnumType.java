package cn.linmt.quiet.jpa.enums.convert;

import cn.linmt.quiet.jpa.enums.*;
import lombok.Getter;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Getter
public enum DatabaseEnumType {
  BYTE(ByteEnum.class, Byte.class),
  DOUBLE(DoubleEnum.class, Double.class),
  FLOAT(FloatEnum.class, Float.class),
  INTEGER(IntegerEnum.class, Integer.class),
  LONG(LongEnum.class, Long.class),
  SHORT(ShortEnum.class, Short.class),
  STRING(StringEnum.class, String.class),
  ;

  private final Class<?> superClass;

  private final Class<?> valueClass;

  DatabaseEnumType(Class<?> superClass, Class<?> valueClass) {
    this.superClass = superClass;
    this.valueClass = valueClass;
  }
}
