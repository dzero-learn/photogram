package com.cos.photogramstart.web.api;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/api/comment/")
    public ResponseEntity<?> comment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        commentDto.setUserId(principalDetails.getUser().getId()); // userId
        Comment comment = commentService.댓글쓰기(commentDto);

        return new ResponseEntity<>(new CMRespDto<Comment>(1,"댓글쓰기 완료",comment), HttpStatus.CREATED);
    }

    @DeleteMapping("/api/comment/")
    public ResponseEntity<?> deleteComment(@RequestBody CommentDto commentDto, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        if(commentDto.getUserId() != principalDetails.getUser().getId()) {
            return new ResponseEntity<>(new CMRespDto<Comment>(0,"댓글을 삭제할 수 없는 유저입니다.",null),HttpStatus.OK);
        }

        commentService.댓글삭제(commentDto);

        return new ResponseEntity<>(new CMRespDto<Comment>(1,"댓글삭제 완료",null),HttpStatus.OK);
    }
}
