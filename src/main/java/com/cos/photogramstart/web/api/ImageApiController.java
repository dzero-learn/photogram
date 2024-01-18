package com.cos.photogramstart.web.api;

import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.service.ImageService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class ImageApiController {
	private final ImageService imageService;
}
