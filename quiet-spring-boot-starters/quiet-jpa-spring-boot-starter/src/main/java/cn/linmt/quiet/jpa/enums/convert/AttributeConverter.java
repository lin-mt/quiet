package cn.linmt.quiet.jpa.enums.convert;

import cn.linmt.quiet.jpa.enums.DatabaseEnum;
import java.io.Serializable;
import net.bytebuddy.implementation.bind.annotation.FieldValue;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;

/**
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
public final class AttributeConverter {

  private AttributeConverter() {}

  @RuntimeType
  public static <T extends Enum<T> & DatabaseEnum<V>, V extends Serializable>
      V convertToDatabaseColumn(T attribute) {
    return attribute == null ? null : attribute.getValue();
  }

  @RuntimeType
  public static <T extends Enum<T> & DatabaseEnum<V>, V extends Serializable>
      T convertToEntityAttribute(V dbData, @FieldValue("enumType") Class<T> enumType) {
    return dbData == null ? null : DatabaseEnum.valueToEnum(enumType, dbData);
  }
}
