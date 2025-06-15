package com.bubbleshop.config.jwt;

import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.member.application.internal.queryservice.MemberQueryService;
import com.bubbleshop.member.domain.model.aggregate.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    private SecretKey secretKey;

    @Value("${jwt.secret-key}")
    private String secretKeyValue;
    @Value("${jwt.expiration-time}")
    private String accessTokenExpirationTime;
    @Value("${jwt.refresh-expiration-time}")
    private String refreshTokenExpirationTime;

    private final MemberQueryService memberQueryService;

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
                .claim(StaticValues.Token.CLAIM_ROLE_KEY, authorities)
                .signWith(this.secretKey, SignatureAlgorithm.HS512)
                .setExpiration(new Date(now.getTime() + Long.parseLong(this.accessTokenExpirationTime)))
                .compact();

        String refreshToken = Jwts.builder()
                .signWith(this.secretKey, SignatureAlgorithm.HS512)
                .setExpiration(new Date(now.getTime() + Long.parseLong(this.refreshTokenExpirationTime)))
                .compact();

        return TokenView.builder().accessToken(accessToken).refreshToken(refreshToken).build();
    }

    public TokenView createToken(Member member) {
        Date now = new Date();

        String accessToken = Jwts.builder()
                .setSubject(member.getId())
                .claim(StaticValues.Token.CLAIM_ROLE_KEY, member.getAuthorities())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + Long.parseLong(this.accessTokenExpirationTime)))
                .signWith(this.secretKey, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .setExpiration(new Date(now.getTime() + Long.parseLong(this.refreshTokenExpirationTime)))
                .signWith(this.secretKey, SignatureAlgorithm.HS512)
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
            return !this.getClaims(token).getExpiration().before(new Date());
        } catch (SecurityException | MalformedJwtException e) {
            e.printStackTrace();
            log.error("TokenProvider validateToken Error ! ", e);
            return false;
        }
    }

    public Authentication getAuthentication(String token) {
        Claims claims =  this.getClaims(token);

        Object authoritiesClaim = claims.get(StaticValues.Token.CLAIM_ROLE_KEY);

        Collection<? extends GrantedAuthority> authorities = Objects.isNull(authoritiesClaim) ?
                AuthorityUtils.NO_AUTHORITIES : AuthorityUtils.commaSeparatedStringToAuthorityList(authoritiesClaim.toString());

        //UserDetails principal = new User(claims.getSubject(), "", authorities);
        UserDetails principal = memberQueryService.getUserDetails(claims.getSubject());

        return new UsernamePasswordAuthenticationToken(principal, EMPTY, authorities);
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(this.secretKey).build().parseClaimsJws(token).getBody();
    }
}
