package com.bubbleshop.config.jwt;

import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.member.domain.model.aggregate.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.bubbleshop.constants.StaticValues.Token.GUEST_ROLE;
import static com.bubbleshop.constants.StaticValues.Token.MEMBER_ROLE;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private SecretKey secretKey;

    @Value("${jwt.secret-key}")
    private String secretKeyValue;
    @Value("${jwt.expiration-time}")
    private Long accessTokenExpirationTime;
    @Value("${jwt.refresh-expiration-time}")
    private Long refreshTokenExpirationTime;

    @PostConstruct
    protected void init() {
        String secret = Base64.getEncoder().encodeToString(secretKeyValue.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public TokenView createMemberToken(Authentication authentication) {
        Date now = new Date();

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(StaticValues.Token.CLAIM_ROLE_KEY, authorities)
                .signWith(this.secretKey, SignatureAlgorithm.HS512)
                .setExpiration(new Date(now.getTime() + this.accessTokenExpirationTime))
                .compact();

        String refreshToken = Jwts.builder()
                .signWith(this.secretKey, SignatureAlgorithm.HS512)
                .setExpiration(new Date(now.getTime() + this.refreshTokenExpirationTime))
                .compact();

        return TokenView.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public TokenView createMemberToken(String memberId) {
        return this.createToken(memberId, MEMBER_ROLE);
    }

    public TokenView createGuestToken(String guestId) {
        return this.createToken(guestId, GUEST_ROLE);
    }

    public TokenView createToken(String subjectId, String role) {
        Date now = new Date();

        Claims claims = Jwts.claims().setSubject(subjectId);
        claims.put(StaticValues.Token.CLAIM_ROLE_KEY, role);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.accessTokenExpirationTime))
                .signWith(this.secretKey, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + this.refreshTokenExpirationTime))
                .signWith(this.secretKey, SignatureAlgorithm.HS512)
                .compact();

        return TokenView.builder()
                .accessToken(accessToken)
                .accessTokenExpirationTime(accessTokenExpirationTime)
                .refreshToken(refreshToken)
                .refreshTokenExpirationTime(refreshTokenExpirationTime)
                .build();
    }

    public String resolveToken(HttpServletRequest request, String tokenName) {
        // Cookie 에서 Access Token 찾기
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (tokenName.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            return !this.getClaims(token).getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException | ExpiredJwtException e) {
            e.printStackTrace();
            log.error("TokenProvider validateToken Error ! ", e);
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims =  this.getClaims(token);

        Object authoritiesClaim = claims.get(StaticValues.Token.CLAIM_ROLE_KEY);
        String id = claims.getSubject();

        Collection<? extends GrantedAuthority> authorities = ObjectUtils.isEmpty(authoritiesClaim) ?
                AuthorityUtils.NO_AUTHORITIES : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        return new UsernamePasswordAuthenticationToken(id, token, authorities);
    }

    public String getUserId(String token) {
        return this.getClaims(token).getSubject();
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody();
    }
}
