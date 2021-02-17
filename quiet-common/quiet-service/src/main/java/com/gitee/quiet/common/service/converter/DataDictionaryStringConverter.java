package com.gitee.quiet.common.service.converter;

import com.gitee.quiet.common.service.base.DataDictionary;
import com.gitee.quiet.common.service.constant.ServiceConstant;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * 数据字典与数据库的转换.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@Converter
public class DataDictionaryStringConverter implements AttributeConverter<DataDictionary, String> {
    
    @Override
    public String convertToDatabaseColumn(DataDictionary attribute) {
        if (attribute != null) {
            return attribute.getType() + ServiceConstant.DataDictionary.SPLIT + attribute.getKey();
        }
        return null;
    }
    
    @Override
    public DataDictionary convertToEntityAttribute(String dbData) {
        if (StringUtils.isNoneBlank(dbData)) {
            String[] split = dbData.split(ServiceConstant.DataDictionary.SPLIT_REGEX);
            if (split.length < ServiceConstant.DataDictionary.ARRAY_MIN_LENGTH) {
                throw new IllegalArgumentException("数据库数据字典有误，数据字典必须包含type和key");
            }
            DataDictionary dataDictionary = new DataDictionary();
            dataDictionary.setType(split[0]);
            dataDictionary.setKey(split[1]);
            return dataDictionary;
        }
        return null;
    }
}
