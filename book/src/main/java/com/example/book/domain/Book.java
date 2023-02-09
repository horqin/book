package com.example.book.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Schema(title = "book")
@Document(indexName = "i_book")
@TableName("t_book")
@Data
public class Book {
    @Schema(title = "主键")
    @Id
    @TableId(type = IdType.AUTO)
    @Null
    private Long id;
    @Schema(title = "篇名")
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    @NotBlank(message = "篇名非空")
    private String title;
    @Schema(title = "作者")
    @NotBlank(message = "作者非空")
    private String authors;
    @Schema(title = "刊名")
    @NotBlank(message = "刊名非空")
    private String publisher;
    @Schema(title = "逻辑删除")
    @TableLogic(value = "0", delval = "1")
    @Null
    private Integer deleted;
}
