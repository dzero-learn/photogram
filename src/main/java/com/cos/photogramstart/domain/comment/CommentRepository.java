package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    /* Comment로 return 받을 수 x, int/Integer만 가능.
     * 문제점 : comment 객체로 return 받을 수 없기 때문에 commentId를 받을 수 없어 pk를 알 수 없다 => 코멘트 삭제 불가능.
     * 해결 : jpa의 save 기능을 사용해야함. -> comment 객체를 return 해줌
    @Modifying
    @Query(value = "INSERT INTO comment (imageId, userId, content, createDate) VALUES (:imageId, :userId, :content, now())", nativeQuery = true)
    Comment mSave(int imageId, int userId, String content);
     */
}
