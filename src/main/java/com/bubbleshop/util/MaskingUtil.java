package com.bubbleshop.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ObjectUtils;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
public class MaskingUtil {

    /**
     * IP 주소 마스킹 처리
     * IPv4: 마지막 옥텟 마스킹 (ex. 192.168.1.*)
     * IPv6: 뒤 4그룹(64비트) 마스킹 (ex. 2001:0db8:85a3:0000:****:****:****:****)
     *
     * @param ip 마스킹할 IP 주소
     * @return 마스킹된 IP 주소
     */
    public static String maskIP(String ip) {
        if (ObjectUtils.isEmpty(ip) || ip.isBlank())
            return EMPTY;

        try {
            InetAddress inetAddress = InetAddress.getByName(ip);

            if (inetAddress instanceof Inet6Address) {
                // IPv6
                return maskIPv6(inetAddress.getAddress());
            } else {
                // IPv4 :: 마지막 8비트(1그룹) 마스킹
                return ip.replaceAll("(\\d+)\\.(\\d+)\\.(\\d+)\\.(\\d+)", "$1.$2.$3.*");
            }
        } catch (UnknownHostException e) {
            return EMPTY;
        }
    }

    /**
     * 축약된 IPv6 주소를 Full Address(8그룹 32자리)로 변환
     *
     * 축약 규칙 준수: IPv6는 0000을 ::으로 축약할 수 있습니다.
     * 마스킹 로직을 짤 때 축약된 주소인지 확인하지 않으면 마스킹 위치가 틀어질 수 있으므로,
     * 반드시 Full Address(32자리)로 변환 후 마스킹하는 것이 안전합니다.
     *
     * ex. 2001:db8::1               -> 2001:0db8:0000:0000:0000:0000:0000:0001
     * ex. ::1                       -> 0000:0000:0000:0000:0000:0000:0000:0001
     * ex. 2001:db8:85a3::8a2e:370:7334 -> 2001:0db8:85a3:0000:0000:8a2e:0370:7334
     */
    private static String maskIPv6(byte[] bytes) {
        // 바이트 배열 -> 8그룹 hex 문자열로 변환
        String[] groups = new String[4];
        for (int i = 0; i < 4; i++) {
            groups[i] = String.format("%04x", ((bytes[i * 2] & 0xFF) << 8) | (bytes[i * 2 + 1] & 0xFF));
        }

        // 앞 4그룹만 남기고 뒤 4그룹 마스킹
        return String.format("%s:%s:%s:%s:****:****:****:****", groups[0],groups[1], groups[2], groups[3]);
    }
}
