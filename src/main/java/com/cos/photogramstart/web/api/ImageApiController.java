package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.web.dto.CMRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.service.ImageService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ImageApiController {
	private final ImageService imageService;

	@PostMapping("/api/story")
	public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails) {
		List<Image> story = imageService.스토리조회(principalDetails.getUser().getId());
		System.out.println(story.toString());

		return new ResponseEntity<>(new CMRespDto<>(1,"스토리 조회 성공",story), HttpStatus.OK);
	}
}
