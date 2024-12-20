package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

@Data
public class CommentDto {
    private int commentId;
    private int imageId;
    private int userId;
    private String content;

    // toEntity가 필요없다.
}
