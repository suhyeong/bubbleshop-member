package com.bubbleshop.config.jwt;

import com.bubbleshop.constants.StaticValues;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
public class TokenProvider {
    private SecretKey secretKey;

    @Value("${jwt.secret-key}")
    private String secretKeyValue;
    @Value("${jwt.expiration-time}")
    private String accessTokenExpirationTime;
    @Value("${jwt.refresh-expiration-time}")
    private String refreshTokenExpirationTime;

    @PostConstruct
    protected void init() {
        String secret = Base64.getEncoder().encodeToString(secretKeyValue.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public TokenView createToken(Authentication authentication) {
        Date now = new Date();

        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim("role", authorities)
                .signWith(this.secretKey, SignatureAlgorithm.HS512)
                .setExpiration(new Date(now.getTime() + Long.parseLong(this.accessTokenExpirationTime)))
                .compact();

        String refreshToken = Jwts.builder()
                .signWith(this.secretKey, SignatureAlgorithm.HS512)
                .setExpiration(new Date(now.getTime() + Long.parseLong(this.refreshTokenExpirationTime)))
                .compact();

        return TokenView.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(StaticValues.Token.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(StaticValues.Token.BEARER_HEADER)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException e) {
            e.printStackTrace();
            log.error("TokenProvider validateToken Error ! ", e);
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Object authoritiesClaim = claims.get(StaticValues.Token.CLAIM_ROLE_KEY);

        Collection<? extends GrantedAuthority> authorities = Objects.isNull(authoritiesClaim) ?
                AuthorityUtils.NO_AUTHORITIES : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

}
