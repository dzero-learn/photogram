package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Modifying
    @Query(value = "DELETE FROM comment WHERE imageId = :imageId AND userId = :principalId", nativeQuery = true)
    void mDelete(int imageId, int principalId);
}
