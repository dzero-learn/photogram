package com.cos.photogramstart.web.api;

import com.cos.photogramstart.service.LikesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.CMRespDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {
	private final ImageService imageService;
	private final LikesService likesService;

	@PostMapping("/api/story")
	public ResponseEntity<?> imageStory(@AuthenticationPrincipal PrincipalDetails principalDetails, @PageableDefault(size=3) Pageable pageable) {
		Page<Image> story = imageService.스토리조회(principalDetails.getUser().getId(), pageable);

		return new ResponseEntity<>(new CMRespDto<>(1,"스토리 조회 성공",story), HttpStatus.OK);
	}

	@PostMapping("/api/image/{imageId}/likes")
	public ResponseEntity<CMRespDto<Object>> likes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		likesService.좋아요(imageId, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1,"좋아요 성공",null),HttpStatus.CREATED);
	}

	@DeleteMapping("/api/image/{imageId}/likes")
	public ResponseEntity<CMRespDto<Object>> unLikes(@PathVariable int imageId, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		likesService.좋아요취소(imageId, principalDetails.getUser().getId());
		return new ResponseEntity<>(new CMRespDto<>(1,"좋아요 취소성공",null),HttpStatus.OK);
	}
}
