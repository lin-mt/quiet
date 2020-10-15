package com.gitee.quite.common.service.converter;

import com.gitee.quite.common.service.base.DataDictionary;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 数据库枚举转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter(autoApply = true)
public class DataDictionaryConverter<T extends DataDictionary<T>>
        implements AttributeConverter<DataDictionary<T>, String> {
    
    @Override
    public String convertToDatabaseColumn(DataDictionary<T> attribute) {
        return attribute == null ? null : attribute.getCode();
    }
    
    @Override
    public DataDictionary<T> convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return null;
        }
        DataDictionary<T> dataDictionary = new DataDictionary<>();
        dataDictionary.setCode(dbData);
        return dataDictionary;
    }
}
