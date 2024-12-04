package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.like.LikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class LikesService {
    private final LikesRepository likesRepository;

    @Transactional
    public void 좋아요(int imageId, int userId) {
        likesRepository.mLikes(imageId,userId);
    }

    @Transactional
    public void 좋아요취소(int imageId, int userId) {
        likesRepository.mUnLikes(imageId,userId);
    }
}