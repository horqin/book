package com.example.book.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;

@Schema(title = "user")
@TableName("t_user")
@Data
public class User {
    @Schema(title = "主键")
    @TableId(type = IdType.AUTO)
    @Null
    private Long id;
    @Schema(title = "用户名")
    @NotBlank
    private String username;
    @Schema(title = "密码")
    @NotBlank
    private String password;
    @TableLogic(value = "0", delval = "1")
    @Schema(title = "逻辑删除")
    @Null
    private Integer deleted;
}
