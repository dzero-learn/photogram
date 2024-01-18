package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
	SecurityFilterChain configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();
    	
    	http.authorizeRequests()
    	.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**,/api/**").authenticated()
    	.anyRequest().permitAll()
    	.and()
    	.formLogin()
    	.loginPage("/auth/signin") // 실제 로그인페이지 경로
    	.loginProcessingUrl("/auth/signinProc") // @PostMapping(value='')에서 value에 들어가는 경로와 동일
    	.defaultSuccessUrl("/");
    	
    	return http.build();
    }
    
}
