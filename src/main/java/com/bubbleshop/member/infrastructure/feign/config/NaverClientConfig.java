package com.bubbleshop.member.infrastructure.feign.config;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.bubbleshop.member.infrastructure.constants.NaverResponseCode;
import com.bubbleshop.member.infrastructure.dto.NaverRspDTO;
import com.bubbleshop.util.FeignClientUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Logger;
import feign.QueryMapEncoder;
import feign.codec.ErrorDecoder;
import feign.querymap.BeanQueryMapEncoder;
import io.netty.handler.codec.http.HttpStatusClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.util.ObjectUtils;

@Slf4j
public class NaverClientConfig {

    @Bean
    Logger.Level loggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public QueryMapEncoder queryMapEncoder(ObjectMapper objectMapper) {
        return new CustomQueryMapEncoder(objectMapper);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return (methodKey, response) -> {
            ResponseCode errorResponse = ResponseCode.SERVER_ERROR;
            int status = FeignClientUtil.getResponseStatus(response);

            if (!HttpStatusClass.SUCCESS.contains(status)) {
                NaverRspDTO rspDTO = FeignClientUtil.getResponseBody(response, NaverRspDTO.class);
                log.error("NaverFeignClient 서버 소통시 에러가 발생했습니다. methodKey : {}, response : {}", methodKey, rspDTO);
                if(!ObjectUtils.isEmpty(rspDTO)) {
                    String errorCode = StringUtils.defaultIfBlank(rspDTO.getError(), rspDTO.getResultCode());
                    errorResponse = NaverResponseCode.find(errorCode);
                }
            }

            return new ApiException(errorResponse);
        };
    }
}
