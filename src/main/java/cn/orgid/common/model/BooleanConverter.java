package cn.orgid.common.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, String>{

	public String convertToDatabaseColumn(Boolean attribute) {
		
		if (Boolean.TRUE.equals(attribute)) {
            return "Y";
        } else {
            return "N";
        }
		
	}

	public Boolean convertToEntityAttribute(String dbData) {
		
		return "Y".equals(dbData);
		
	}

}
