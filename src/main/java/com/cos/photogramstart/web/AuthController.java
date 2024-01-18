package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignUpDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AuthController {

	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
	private final AuthService authService;
	
//	public AuthController(AuthService authService) {
//		this.authService = authService;
//	}

	@GetMapping("/auth/signin")
	String signInForm() {
		return "/auth/signin";
	}
	
	@GetMapping("/auth/signup")
	String signUpForm() {
		return "/auth/signup";
	}
	
	@PostMapping("/auth/signup")
	String signUp(@Valid SignUpDto signupDto, BindingResult bindingResult) {
		log.info(signupDto.toString());
		
		if(bindingResult.hasErrors()) {
			Map<String, String> errorMap = new HashMap<String, String>();
			
			for(FieldError e : bindingResult.getFieldErrors()) {
				errorMap.put(e.getField(), e.getDefaultMessage());
			}
			
			// 오류를 던진다
			throw new CustomValidationException("유효성 검사 실패함", errorMap);
		} else {
			User user = signupDto.toEntity();
			User userEntity = authService.회원가입(user);
			
			return "/auth/signin";
		}
	}
}
