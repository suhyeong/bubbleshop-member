package com.bubbleshop.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class EncodeUtil {
    public static String encodeStringWithUTF8(String message) {
        return URLEncoder.encode(message, StandardCharsets.UTF_8);
    }
}
