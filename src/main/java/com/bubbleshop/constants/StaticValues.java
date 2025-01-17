package com.bubbleshop.constants;

public class StaticValues {
    public static final String RESULT_CODE = "resultCode";
    public static final String RESULT_MESSAGE = "resultMessage";

    public static final String REPLACE_FIRST_STRING = "{0}";

    public static final String COMMON_Y = "Y";
    public static final String COMMON_N = "N";

    public static class RedisKey {
        public static final long REDIS_DEFAULT_EXPIRE_SEC = 600L; // 600초
    }

    public static class Profiles {
        public static final String localProfile = "default|local";
    }

    public static final String S3_TEMP_FOLDER = "temp/";

    public static class ImageStatus {
        public static final String STAY = "STAY";
        public static final String DELETE = "DELETE";
        public static final String ADD = "NEW";
    }
}
