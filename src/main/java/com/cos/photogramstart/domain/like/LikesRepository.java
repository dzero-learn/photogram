package com.cos.photogramstart.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Integer> {
    @Modifying
    @Query(value = "INSERT INTO likes (imageId, userId, createDate) VALUES (:imageId, :userId, now())", nativeQuery = true)
    void mLikes(@Param("imageId") int imageId, @Param("userId") int userId);

    @Modifying
    @Query(value = "DELETE FROM likes WHERE imageId = :imageId AND userId = :userId", nativeQuery = true)
    void mUnLikes(@Param("imageId") int imageId, @Param("userId") int userId);
}