package com.bubbleshop.member.domain.model.converter;


import com.bubbleshop.constants.StaticValues;

import javax.persistence.AttributeConverter;

public class YOrNToBooleanConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return attribute ? StaticValues.COMMON_Y : StaticValues.COMMON_N;
    }

    @Override
    public Boolean convertToEntityAttribute(String dbData) {
        return dbData.equals(StaticValues.COMMON_Y);
    }
}
