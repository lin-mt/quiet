package cn.linmt.quiet.system.converter;

import cn.linmt.quiet.system.entity.Dictionary;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 数据字典与数据库的转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter(autoApply = true)
public class DictionaryStringConverter implements AttributeConverter<Dictionary, String> {

  @Override
  public String convertToDatabaseColumn(Dictionary attribute) {
    if (attribute != null) {
      return attribute.getKey();
    }
    return null;
  }

  @Override
  public Dictionary convertToEntityAttribute(String dbData) {
    Dictionary dict = new Dictionary();
    dict.setKey(dbData);
    return dict;
  }
}
