package com.example.book.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

@Schema(title = "bookReview")
@Document(collection = "c_bookReview")
@Data
public class BookReview {
    @Schema(title = "主键")
    @Id
    @Null
    private String id;
    @Schema(title = "文章主键")
    @Field(targetType = FieldType.INT64)
    @NotNull
    private Long bookId;
    @Schema(title = "用户名")
    @Null
    private String username;
    @Schema(title = "评论")
    @NotBlank
    private String review;
    @Schema(title = "回复列表")
    @Null
    private List<BookReviewReply> replies;
}
