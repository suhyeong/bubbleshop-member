package com.bubbleshop.util;

import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.exception.ApiException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import feign.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class FeignClientUtil {

    /**
     * Header 이름으로 Value 를 가져온다.
     * Header 가 비어있거나 Value 가 비어있는 경우 빈 값 "" 을 리턴한다.
     * @param response 응답 Response Entity
     * @param headerName 헤더명
     * @return 헤더의 value
     */
    public static String getHeaderValue(Response response, String headerName) {
        if(response.headers().isEmpty())
            return StringUtils.EMPTY;

        Map<String, Collection<String>> headers = response.headers();
        Collection<String> values = headers.get(headerName);
        if(Objects.nonNull(values) && !values.isEmpty()) {
            String[] array = new String[values.size()];
            values.toArray(array);
            return array[0];
        }

        return StringUtils.EMPTY;
    }

    public static int getResponseStatus(Response response) {
        return response.status();
    }

    public static <T> T getResponseBody(Response response, Class<T> mappingClass) {
        if(response.body().length() == 0)
            return null;

        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(bodyIs, mappingClass);
        } catch (Exception e) {
            log.error("FeignClient ResponseBody Mapper 오류 발생, ", e);
            throw new ApiException(ResponseCode.SERVER_ERROR);
        }
    }
}
