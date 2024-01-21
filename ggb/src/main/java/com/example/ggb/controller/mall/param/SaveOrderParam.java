package com.example.ggb.controller.mall.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author dulred
 * @description
 * @github https://github.com/dulred
 */
@Data
public class SaveOrderParam implements Serializable {

    @ApiModelProperty("订单项id数组")
    private Long[] cartItemIds;

    @ApiModelProperty("地址id")
    private Long addressId;

}
