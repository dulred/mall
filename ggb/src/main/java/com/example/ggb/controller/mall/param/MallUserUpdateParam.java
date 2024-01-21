
package com.example.ggb.controller.mall.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 用户修改param
 */
@Data
public class MallUserUpdateParam implements Serializable {

    @ApiModelProperty("用户昵称")
    @Size(min = 1, max = 20, message = "Name must be between 1 and 20 characters")
    private String nickName;

    @ApiModelProperty("用户密码(需要SHA256加密)")
    private String passwordSha256;

    @ApiModelProperty("个性签名")
    @Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")
    private String introduceSign;

}
