package com.bubbleshop.member.domain.model.converter;

import com.bubbleshop.member.domain.constant.MemberJoinPlatformType;
import jakarta.persistence.AttributeConverter;

public class MemberJoinPlatformTypeConverter implements AttributeConverter<MemberJoinPlatformType, String> {
    @Override
    public String convertToDatabaseColumn(MemberJoinPlatformType attribute) {
        return attribute.getCode();
    }

    @Override
    public MemberJoinPlatformType convertToEntityAttribute(String dbData) {
        return MemberJoinPlatformType.find(dbData);
    }
}
