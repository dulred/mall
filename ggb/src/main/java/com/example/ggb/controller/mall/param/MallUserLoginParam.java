package com.example.ggb.controller.mall.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 用户登录param
 */
@Data
public class MallUserLoginParam implements Serializable {
    @ApiModelProperty("登录名")
    @NotEmpty(message="登录名不能为空")
    private String loginName;
    @ApiModelProperty("登录密码")
    @NotEmpty(message="密码不能为空")
    private String passwordSha256;

}
