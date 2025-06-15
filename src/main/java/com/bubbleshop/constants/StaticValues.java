package com.bubbleshop.constants;

public class StaticValues {
    public static final String RESULT_CODE = "resultCode";
    public static final String RESULT_MESSAGE = "resultMessage";

    public static final String REPLACE_FIRST_STRING = "{0}";

    public static final String COMMON_Y = "Y";
    public static final String COMMON_N = "N";

    public static final String ADMIN = "admin"; // TODO

    public static class Prefix {
        public static final String COMMENT_NO_PREFIX = "C";
    }

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

    public static class Token {
        public static final String AUTHORIZATION_HEADER = "Authorization";
        public static final String BEARER_HEADER = "Bearer ";
        public static final String CLAIM_ROLE_KEY = "role";
        public static final String ADMIN_ROLE = "admin";
        public static final String MEMBER_ROLE = "member";
    }
}
