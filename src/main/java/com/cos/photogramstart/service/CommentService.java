package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
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
    public Comment 댓글쓰기(String content, int imageId, int userId) {
        // Tip: 빈객체를 만들 때 id값만 담아서 insert 할 수 있다.
        // 어차피 db는 id값만 들어가면 되므로 굳이 findById() 사용 할 필요x
        // 대신 return 시에 image객체는 id값만 가지고 있는 빈 객체를 리턴받는다.
        Image images = new Image();
        images.setId(imageId);

        User userEntity = userRepository.findById(userId).orElseThrow(() -> {
            throw new CustomException("유저 아이디를 찾을 수 없습니다.");
        });

        // 영속화 저장 사용
        Comment commentEntity = new Comment();
        commentEntity.setContent(content);
        commentEntity.setImage(images);
        commentEntity.setUser(userEntity);

        return commentRepository.save(commentEntity);
    }

    @Transactional
    public void 댓글삭제(int id) {
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            // cf. customException : html 파일 리턴하는 컨트롤러
            // customApiException : 데이터 리턴 컨트롤러
            throw new CustomApiException(e.getMessage());
        }
    }
}
