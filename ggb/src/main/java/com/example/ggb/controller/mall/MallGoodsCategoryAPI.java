package com.example.ggb.controller.mall;

import com.example.ggb.common.MallException;
import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.controller.mall.vo.MallIndexCategoryVO;
import com.example.ggb.service.MallCategoryService;
import com.example.ggb.util.Result;
import com.example.ggb.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(value = "v1", tags = "商城分类页面接口")
@RequestMapping("/api/v1")
public class MallGoodsCategoryAPI {

    @Resource
    private MallCategoryService MallCategoryService;

    @GetMapping("/categories")
    @ApiOperation(value = "获取分类数据", notes = "分类页面使用")
    public Result<List<MallIndexCategoryVO>> getCategories() {
        List<MallIndexCategoryVO> categories = MallCategoryService.getCategoriesForIndex();
        if (CollectionUtils.isEmpty(categories)) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return ResultGenerator.genSuccessResult(categories);
    }
}
