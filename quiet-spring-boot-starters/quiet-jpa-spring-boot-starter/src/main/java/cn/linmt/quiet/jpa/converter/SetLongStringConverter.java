package cn.linmt.quiet.jpa.converter;

import com.google.common.base.Joiner;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * Set<Long> 与 String 的转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter(autoApply = true)
public class SetLongStringConverter implements AttributeConverter<Set<Long>, String> {

  private static final String DELIMITER = ",";

  @Override
  public String convertToDatabaseColumn(Set<Long> attribute) {
    if (CollectionUtils.isEmpty(attribute)) {
      return null;
    }
    return Joiner.on(DELIMITER).join(attribute);
  }

  @Override
  public Set<Long> convertToEntityAttribute(String dbData) {
    Set<Long> attribute = new HashSet<>();
    if (StringUtils.isBlank(dbData)) {
      return attribute;
    }
    StringTokenizer st = new StringTokenizer(dbData, DELIMITER);
    while (st.hasMoreTokens()) {
      attribute.add(Long.parseLong(st.nextToken()));
    }
    return attribute;
  }
}
