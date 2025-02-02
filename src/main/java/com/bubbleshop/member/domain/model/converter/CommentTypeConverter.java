package com.bubbleshop.member.domain.model.converter;

import com.bubbleshop.member.domain.constant.CommentType;

import javax.persistence.AttributeConverter;

public class CommentTypeConverter implements AttributeConverter<CommentType, String> {
    @Override
    public String convertToDatabaseColumn(CommentType attribute) {
        return attribute.getCode();
    }

    @Override
    public CommentType convertToEntityAttribute(String dbData) {
        return CommentType.find(dbData);
    }
}
