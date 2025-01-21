package com.bubbleshop.util;

import feign.Response;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

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
}
