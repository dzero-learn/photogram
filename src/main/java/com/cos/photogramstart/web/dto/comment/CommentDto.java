package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CommentDto {
    // commentApiController @Valid 걸어줘야함
    @NotBlank
    private int imageId;
    @NotBlank
    private String content;

    // toEntity가 필요없다.
}
