package com.bubbleshop.config;

import com.bubbleshop.config.jwt.AuthenticationRequiredFilter;
import com.bubbleshop.config.jwt.JwtAuthenticationFilter;
import com.bubbleshop.config.jwt.RefreshTokenFilter;
import com.bubbleshop.config.jwt.TokenProvider;
import com.bubbleshop.constants.ResponseCode;
import com.bubbleshop.constants.StaticValues;
import com.bubbleshop.util.EncodeUtil;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

import static com.bubbleshop.constants.StaticHeaders.BACKOFFICE_CHANNEL;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(FormLoginConfigurer::disable)
                .logout(LogoutConfigurer::disable)
                // Cookie 사용으로 CSRF 보호 (SameSite=Strict 설정으로 disable 처리)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> {
                    CorsConfigurationSource source = request -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(List.of("http://localhost:3001")); // TODO 운영 도메인 추가
                        // 허용 Method 조건 걸 수 있음 (GET, POST, PUT, PATCH, OPTIONS)
                        config.setAllowedMethods(List.of("*"));
                        // Cookie 사용 허용
                        config.setAllowCredentials(true);

                        config.setExposedHeaders(List.of("Set-Cookie", "Authorization"));

                        return config;
                    };
                    cors.configurationSource(source);
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 생성 및 사용 X
                })
                .securityMatcher(request -> !request.getHeader(HttpHeaders.FROM).equals(BACKOFFICE_CHANNEL))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/actuator/health").permitAll() // health check
                            .requestMatchers("/auth/v1/auth").permitAll() // create token, refresh token
                            .anyRequest().authenticated()) // 그 외 모든 API 는 인증 필요
                .addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class) // jwt 인증 필터 적용
                .addFilterAfter(new RefreshTokenFilter(tokenProvider), JwtAuthenticationFilter.class)
                .addFilterAfter(new AuthenticationRequiredFilter(), RefreshTokenFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authenticationException) -> {
                            response.setStatus(ResponseCode.UNAUTHORIZED.getStatus().value());
                            response.setHeader(StaticValues.RESULT_CODE, ResponseCode.UNAUTHORIZED.getCode());
                            response.setHeader(StaticValues.RESULT_MESSAGE, EncodeUtil.encodeStringWithUTF8(ResponseCode.UNAUTHORIZED.getMessage()));
                            response.setContentType("application/json");
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN); // TODO
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"Access denied\"}");
                        })
                )
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
