package com.cos.photogramstart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration // Ioc 등록
public class WebMvcConfig implements WebMvcConfigurer{ // web 설정파일
	
	@Value("${file.path}") // application.yml에 설정된 file 경로 가지고옴.
	private String uploadFolder;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		
		registry
		.addResourceHandler("/upload/**") // jsp페이지에서 /upload/** 주소 패턴이 나오면 발동.
		.addResourceLocations("file:///"+uploadFolder) // file:///c:/~ 경로로 바뀜
		.setCachePeriod(60*10*6) // 1시간 이미지 캐시
		.resourceChain(true)
		.addResolver(new PathResourceResolver());
		
	}
}
