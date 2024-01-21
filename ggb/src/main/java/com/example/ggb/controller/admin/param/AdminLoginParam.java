package com.example.ggb.controller.admin.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author dulred
 * @description
 * @github https://github.com/dulred
 */
@Data
public class AdminLoginParam implements Serializable {

    @ApiModelProperty("登录名")
    @NotEmpty(message="登录名不能为空")
    private String userName;

    @ApiModelProperty("登录密码(需要SHA256加密)")
    @NotEmpty(message="密码不能为空")
    private String passwordSha256;




}
