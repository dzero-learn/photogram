package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	private final UserService userServcie;

	@PutMapping("/api/user/{principalId}/profileImageUrl")
	public ResponseEntity<?> profileImageUrlUpdate (@PathVariable int principalId, MultipartFile profileImageFile, @AuthenticationPrincipal PrincipalDetails principalDetails) { // form태그의 name필드 값을 매개변수명으로 잡아줘야함

		if(profileImageFile.isEmpty()) {
			throw new CustomValidationApiException("파일이 첨부되지 않았습니다.", null);
		}

		User userEntity = userServcie.회원프로필사진변경(principalId, profileImageFile);
		// 프로필사진이 변경되면 세션도 변경해줘야함 -> 매개변수:@AuthenticationPrincipal 추가
		principalDetails.setUser(userEntity);

		return new ResponseEntity<>(new CMRespDto<>(1,"프로필사진변경 성공",null), HttpStatus.OK);
	}

	@PutMapping("/api/update/{id}")
	public CMRespDto<?> update(@PathVariable int id,
			@Valid UserUpdateDto userUpdateDto,
			BindingResult bindingResult, // 체크 할 매개변수 다음에 적어줘야함!!
			@AuthenticationPrincipal PrincipalDetails principalDetails) {

		Map<String, String> errorMap = new HashMap<String, String>();

		if (bindingResult.hasErrors()) {
			for (FieldError error : bindingResult.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}

			throw new CustomValidationApiException("유효성 검사 실패함", errorMap);
		} else {
			User userEntity = userServcie.회원수정(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity);

			return new CMRespDto<User>(1, "회원정보 수정완료!", userEntity);
		}
	}
}
