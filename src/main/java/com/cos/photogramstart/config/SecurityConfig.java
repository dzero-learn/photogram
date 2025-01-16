package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // 해당 파일로 시큐리티를 활성화
@Configuration // Ioc
public class SecurityConfig {
	
	private final OAuth2DetailsService oAuth2DetailsServicw;

	@Bean
	BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
    	// super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화 됨.
    	http.csrf().disable();
    	
    	http.authorizeRequests()
    	.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**,/api/**").authenticated()
    	.anyRequest().permitAll()
    	.and()
    	.formLogin()
    	.loginPage("/auth/signin") // 실제 로그인페이지 경로
    	.loginProcessingUrl("/auth/signinProc") // @PostMapping(value='')에서 value에 들어가는 경로와 동일
    	.defaultSuccessUrl("/")
    	.and()
    	.oauth2Login() // form로그인도 하는데, oauth2로그인도 할거야!!
    	.userInfoEndpoint() // oauth2 로그인 최종응답을 회원정보를 바로 받을 수 있다.
    	.userService(oAuth2DetailsServicw);
    	
    	return http.build();
    }
    
}
