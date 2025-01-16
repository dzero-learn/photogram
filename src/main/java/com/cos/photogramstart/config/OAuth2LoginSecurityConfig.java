package com.cos.photogramstart.config;

import com.cos.photogramstart.service.CustomOAuth2UserService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers("/", "/auth/signin").permitAll() // 홈 및 로그인 페이지는 누구나 접근 가능
                                .anyRequest().authenticated()          // 나머지 요청은 인증 필요
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/auth/signin") // 커스텀 로그인 페이지
                                .defaultSuccessUrl("/") // 로그인 성공 후 리다이렉트 경로
                                .userInfoEndpoint(userInfoEndpoint ->
                                        userInfoEndpoint
                                                .userService(new CustomOAuth2UserService()) // 사용자 정보 서비스
                                )
                );
    }
}
