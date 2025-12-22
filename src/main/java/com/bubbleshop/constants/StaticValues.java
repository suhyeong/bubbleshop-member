package com.bubbleshop.constants;

public class StaticValues {
    public static final String RESULT_CODE = "resultCode";
    public static final String RESULT_MESSAGE = "resultMessage";

    public static final String REPLACE_FIRST_STRING = "{0}";

    public static final String COMMON_Y = "Y";
    public static final String COMMON_N = "N";

    public static final String ADMIN = "admin"; // TODO
    public static final String DASH = "-";

    public static class Prefix {
        public static final String COMMENT_NO_PREFIX = "C";
        public static final String MEMBER_ID_PREFIX = "M";
    }

    public static class RedisKey {
        public static final long REDIS_DEFAULT_EXPIRE_SEC = 600L; // 600초
        public static final String REFRESH_TOKEN_KEY = "rtk";
        public static final String STATE_KEY = "stt";

        public static final String REDIS_KEY_DIVIDER = ":";
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
        public static final String CLAIM_ROLE_KEY = "role";
        public static final String MEMBER_ROLE = "member_role";
        public static final String GUEST_ROLE = "guest_role";
        public static final String GUEST_ID_PREFIX = "G";

        public static final String ACCESS_TOKEN = "access_token";
        public static final String REFRESH_TOKEN = "refresh_token";
        public static final String SAME_SITE_ATTRIBUTE = "SameSite";
        public static final int TOKEN_SECONDS = 1000;

        public static final String MEMBER_ID_REQUEST = "mbrId";
    }
}
