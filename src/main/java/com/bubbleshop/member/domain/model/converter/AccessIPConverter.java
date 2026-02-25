package com.bubbleshop.member.domain.model.converter;


import com.bubbleshop.util.MaskingUtil;
import jakarta.persistence.AttributeConverter;

public class AccessIPConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return MaskingUtil.maskIP(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}
