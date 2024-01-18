package com.cos.photogramstart.service;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder BCryptPasswordEncoder;

	public UserProfileDto 회원프로필(int pageUserId, int principalId) {
		UserProfileDto dto = new UserProfileDto();
		
		// 왜 imageRepository에서 안 찾아?
		User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
			}
		);
		
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId == principalId); // 사진등록or구독하기 표시
		dto.setImageCount(userEntity.getImages().size()); // 게시물 갯수
		
		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		
		dto.setSubscribeState(subscribeState == 1);
		dto.setSubscribeCount(subscribeCount);
		
		return dto;
	}
	
	@Transactional
	public User 회원수정(int userId, User user) {
		// 1. 영속화
		User userEntity = userRepository.findById(userId).orElseThrow(()->{return new CustomValidationApiException("찾는 id가 없습니다.");});
		
		// 2. 영속화된 객체를 수정 -> 더티 체킹
		userEntity.setPassword(BCryptPasswordEncoder.encode(user.getPassword()));
		userEntity.setName(user.getName());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setBio(user.getBio());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());

		return userEntity;
	} // 더티체킹이 일어나서 자동으로 db update 완료됨.
}
