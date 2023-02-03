package com.example.book.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.util.List;

@Schema(title = "bookReview")
@Document(collection = "c_bookReview")
@Data
public class BookReview {
    @Schema(title = "主键")
    @Id
    private String id;
    @Schema(title = "文章主键")
    @Field(targetType = FieldType.INT64)
    private Long bookId;
    @Schema(title = "用户名")
    private String username;
    @Schema(title = "评论")
    private String review;
    @Schema(title = "回复列表")
    private List<BookReviewReply> replies;
}
