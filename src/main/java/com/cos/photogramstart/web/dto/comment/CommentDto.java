package com.cos.photogramstart.web.dto.comment;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @NotNull : 값이 null이 아니여야함. -> null 불가
 * @NotBlank : 값이 null이 아니고, 공백을 제외한 실제 문자가 있는 문자열 -> null, "", " " 모두 불가
 * @NotEmpty : 값이 null이 아니고, 길이가 0이 아닌 문자열 -> null, "" 불가
 * **/
@Data
public class CommentDto {
    // commentApiController @Valid 걸어줘야함
    @NotNull
    private int imageId;
    @NotBlank
    private String content;

    // toEntity가 필요없다.
}
