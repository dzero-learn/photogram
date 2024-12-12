package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public Comment 댓글쓰기(CommentDto commentDto) {
        Image images = new Image();
        images.setId(commentDto.getImageId());

        User userEntity = userRepository.findById(commentDto.getUserId()).orElseThrow(() -> {
            throw new CustomException("유저를 찾을 수 없습니다.");
        });

        // 영속화 저장 사용
        Comment commentEntity = new Comment();
        commentEntity.setImage(images);
        commentEntity.setUser(userEntity);
        commentEntity.setContent(commentDto.getContent());

        return commentRepository.save(commentEntity);
    }

    @Transactional
    public void 댓글삭제(CommentDto commentDto) {
        commentRepository.mDelete(commentDto.getImageId(), commentDto.getUserId());
    }
}
