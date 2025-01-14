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

	// DI(의존성주입) 사용 시, 반드시 편하게 갖다 쓸 객체들은 Ioc컨테이너에 등록되어 있어야 가져다 쓸 수 있음.
	// service객체 DI(의존성주입) 방법 1.
	// @RequiredArgsContructor 어노테이션 생성(생성자를 만들어주면서 그 안에 final 달린 애들을 모두 초기화 선언해줌)
	// final 객체 선언
	private final AuthService authService;

// service객체 DI(의존성주입) 방법 2. 추천x
// 직접 AuthController 생성자 선언하고 authService 객체 주입
//	private AuthService authService;
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

		User user = signupDto.toEntity();
		User userEntity = authService.회원가입(user);

		return "/auth/signin";
	}
}
