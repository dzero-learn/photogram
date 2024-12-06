package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	
	@GetMapping("/user/{pageUserId}")
	String profile(@PathVariable int pageUserId, Model model, @AuthenticationPrincipal PrincipalDetails principalDetails){
		UserProfileDto dto = userService.회원프로필(pageUserId, principalDetails.getUser().getId());

		// UserProfileDto에 likeCount를 만들 수x 이미지마다 좋아요갯수를 들고 있어야되는데 유저에 만들어버리면 좋아요갯수 1개밖에 안들고 있음
		// likeCount를 어케 가지고 올 수 있을까? -> userService.회원프로필()에서 구현
		model.addAttribute("dto", dto);

		return "/user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	String update(@PathVariable String id, @AuthenticationPrincipal PrincipalDetails principalDetails){
		// ? 그럼 username으로 회원정보를 또 찾아야되는건가?
		// 가 아니고 내가 principal 클래스에 user 게터세터를 안 만들었네ㅎ
		//System.out.println("세션 정보 : " + principalDetails.getUser());
		
		return "/user/update";
	}
}
