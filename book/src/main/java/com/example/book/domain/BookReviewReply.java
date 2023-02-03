package com.example.book.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(title = "bookReviewReply")
@Data
public class BookReviewReply {
    @Schema(title = "用户名")
    private String username;
    @Schema(title = "回复")
    private String reply;
}
