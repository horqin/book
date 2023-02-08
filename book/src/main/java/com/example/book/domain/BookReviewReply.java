package com.example.book.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Schema(title = "bookReviewReply")
@Data
public class BookReviewReply {
    @Schema(title = "用户名")
    @Null
    private String username;
    @Schema(title = "回复")
    @NotBlank
    private String reply;
}
