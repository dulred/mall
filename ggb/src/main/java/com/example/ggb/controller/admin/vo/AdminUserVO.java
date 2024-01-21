package com.example.ggb.controller.admin.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author dulred
 * @description
 * @github https://github.com/dulred
 */

@Data
public class AdminUserVO {

    @ApiModelProperty("用户昵称")
    private String nickName;

    @ApiModelProperty("用户登录名")
    private String loginUserName;
}
