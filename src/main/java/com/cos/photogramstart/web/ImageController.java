package com.cos.photogramstart.web;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService;
	
	@GetMapping({"/","/image/story"})
	String story(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		// 팔로우 user 정보
		// 팔로우 user 업로드 이미지
		// 팔로우 user 이미지 좋아요 수
		// 팔로우 user 댓글
		List<Image> story = imageService.스토리조회(principalDetails.getUser().getId());
		
		//System.out.println(story);
		
		model.addAttribute("story", story);
		
		return "/image/story";
	}
	
	@GetMapping("/image/popular")
	String popular() {
		return "/image/popular";
	}
	
	@GetMapping("/image/upload")
	String upload() {
		return "/image/upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(imageUploadDto.getFile().isEmpty()) {
			throw new CustomValidationException("이미지가 첨부되지 않았습니다.",null);
		}
		
		imageService.사진업로드(imageUploadDto, principalDetails);
		
		return "redirect:user/"+principalDetails.getUser().getId();
	}
}