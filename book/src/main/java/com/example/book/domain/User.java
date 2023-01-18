package com.example.book.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(title = "user")
@TableName("t_user")
@Data
public class User {
    @Schema(title = "主键")
    @TableId(type = IdType.AUTO)
    private Long id;
    @Schema(title = "用户名")
    private String username;
    @Schema(title = "密码")
    private String password;
    @TableLogic(value = "0", delval = "1")
    @Schema(title = "逻辑删除")
    private Integer deleted;
}
