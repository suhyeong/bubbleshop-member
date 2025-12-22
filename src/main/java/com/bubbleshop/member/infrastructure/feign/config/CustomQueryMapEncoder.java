package com.bubbleshop.member.infrastructure.feign.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.QueryMapEncoder;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class CustomQueryMapEncoder implements QueryMapEncoder {
    private final ObjectMapper objectMapper;

    @Override
    public Map<String, Object> encode(Object object) {
        return objectMapper.convertValue(object, new TypeReference<Map<String, Object>>() {});
    }
}
