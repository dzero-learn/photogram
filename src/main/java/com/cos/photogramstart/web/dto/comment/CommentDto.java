package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

@Data
public class CommentDto {
    private int imageId;
    private int userId;
    private String content;
}
