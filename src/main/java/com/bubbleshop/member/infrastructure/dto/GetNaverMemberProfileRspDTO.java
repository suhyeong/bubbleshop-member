package com.bubbleshop.member.infrastructure.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class GetNaverMemberProfileRspDTO extends NaverRspDTO {
    @Serial
    private static final long serialVersionUID = 2905765220717415806L;

    @JsonProperty("response")
    private GetNaverMemberProfileResult result;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class GetNaverMemberProfileResult implements Serializable {
        @Serial
        private static final long serialVersionUID = 9068712678976993816L;

        @JsonProperty("id")
        private String id;

        @JsonProperty("name")
        private String name;

        @JsonProperty("birthday")
        private String birthday; // MM-dd 10-01

        @JsonProperty("mobile")
        private String mobile; // 010-0000-0000

        @JsonProperty("email")
        private String email;
    }
}
