package com.bubbleshop.member.domain.model.converter;

import com.bubbleshop.member.domain.constant.HistoryType;

import jakarta.persistence.AttributeConverter;

public class HistoryTypeConverter implements AttributeConverter<HistoryType, String> {
    @Override
    public String convertToDatabaseColumn(HistoryType attribute) {
        return attribute.getCode();
    }

    @Override
    public HistoryType convertToEntityAttribute(String dbData) {
        return HistoryType.find(dbData);
    }
}
