package com.cos.photogramstart.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	private final ImageRepository imageRepository;
	private final EntityManager em;
	
	@Value("${file.path}") // application.yml에 설정된 file:path: 값을 가지고 옴
	private String uploadFolder;
	
	@Transactional(readOnly = true)
	public List<Image> 스토리조회(int principalId) {
		// 구독정보 조회
		List<Image> story = imageRepository.mStory(principalId);
		
		return story;
	}
	
	@Transactional
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
		UUID uuid = UUID.randomUUID();
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename();
		
		System.out.println("이미지 파일 이름:"+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser());
		Image imageEntity = imageRepository.save(image);
		
		//System.out.println(imageEntity);
	}
}
