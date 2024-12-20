package com.cos.photogramstart.web.api;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.SubscribeService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SubscribeApiController {

	private final SubscribeService subscribeService;
	
	@PostMapping("/api/subscribe/{toUserId}")
	public ResponseEntity<?> subscribe(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int toUserId) {
		//구독하기
		subscribeService.구독하기(principalDetails.getUser().getId(), toUserId);
		
		return new ResponseEntity<>(new CMRespDto<>(1,"구독 완료",null),HttpStatus.OK);
	}
	
	@DeleteMapping("/api/unsubscribe/{toUserId}")
	public ResponseEntity<?> unSubscribe(@AuthenticationPrincipal PrincipalDetails principalDetails , @PathVariable int toUserId) {
		//구독취소하기
		subscribeService.구독취소하기(principalDetails.getUser().getId(), toUserId);
		
		return new ResponseEntity<>(new CMRespDto<>(1,"구독취소 완료",null),HttpStatus.OK);
	}
	
	@PostMapping("/api/user/{pageUserId}/subscribe")
	public ResponseEntity<?> subscribeList(@AuthenticationPrincipal PrincipalDetails principalDetails, @PathVariable int pageUserId) {
		System.out.println("뭐야");
		//구독하기
		List<SubscribeDto> list = subscribeService.구독리스트(principalDetails.getUser().getId(), pageUserId);
		
		return new ResponseEntity<>(new CMRespDto<>(1,"구독리스트 조회완료",list),HttpStatus.OK);
	} 
}
