package com.bubbleshop.member.domain.model.converter;

import com.bubbleshop.member.domain.constant.MemberProviderType;
import jakarta.persistence.AttributeConverter;

public class MemberJoinPlatformTypeConverter implements AttributeConverter<MemberProviderType, String> {
    @Override
    public String convertToDatabaseColumn(MemberProviderType attribute) {
        return attribute.getCode();
    }

    @Override
    public MemberProviderType convertToEntityAttribute(String dbData) {
        return MemberProviderType.find(dbData);
    }
}
