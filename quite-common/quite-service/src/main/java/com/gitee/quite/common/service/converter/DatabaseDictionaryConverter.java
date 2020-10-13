package com.gitee.quite.common.service.converter;

import com.gitee.quite.common.service.base.DatabaseDictionary;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 数据库枚举转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter(autoApply = true)
public class DatabaseDictionaryConverter implements AttributeConverter<DatabaseDictionary<?>, String> {
    
    @Override
    public String convertToDatabaseColumn(DatabaseDictionary<?> attribute) {
        return attribute == null ? null : attribute.getCode();
    }
    
    @Override
    public DatabaseDictionary<?> convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return null;
        }
        DatabaseDictionary<?> databaseDictionary = new DatabaseDictionary<>();
        databaseDictionary.setCode(dbData);
        return databaseDictionary;
    }
}
