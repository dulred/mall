package com.example.ggb.controller.admin;

import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.config.annotation.TokenToAdminUser;
import com.example.ggb.controller.admin.param.BatchIdParam;
import com.example.ggb.controller.admin.param.CarouselAddParam;
import com.example.ggb.controller.admin.param.CarouselEditParam;
import com.example.ggb.entity.AdminUser;
import com.example.ggb.entity.Carousel;
import com.example.ggb.service.MallCarouselService;
import com.example.ggb.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @author dulred
 * @description
 * @github https://github.com/dulred
 */
@RestController
@Api(value = "v1",tags = "后台管理系统轮播图模块接口")
@RequestMapping("/manage-api/v1")
public class AdminCarouselAPI {

    private static final Logger logger = LoggerFactory.getLogger(AdminCarouselAPI.class);

    @Resource
    MallCarouselService mallCarouselService;

    @PostMapping("/carousels")
    @ApiOperation("新增轮播图")
    public Result<String> saveCarousel (@RequestBody @Valid CarouselAddParam carouselAddParam, @TokenToAdminUser AdminUser adminUser){
        Carousel carousel = new Carousel();
        BeanUtil.copyProperties(carouselAddParam,carousel);
        if (mallCarouselService.saveCarousel(carousel))
        {
            return ResultGenerator.genSuccessResult();
        }

        return ResultGenerator.genFailResult(ServiceResultEnum.DB_ERROR.getResult());
    }


    @GetMapping("/carousels")
    @ApiOperation("轮播图列表")
    public Result<PageResult> list (
            @RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
            @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
            @TokenToAdminUser AdminUser adminUser
            ){
        logger.info("pageNumber {} pageSize {}" ,pageNumber,pageSize);
        if ((pageNumber==null) || (pageNumber<1) || pageSize == null || pageSize>10){
            return ResultGenerator.genFailResult("分页参数异常！");
        }
        Map params = new HashMap(4);
        params.put("page", pageNumber);
        params.put("limit",pageSize);
        PageQueryUtil pageQueryUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(mallCarouselService.getCarouselPage(pageQueryUtil));

    }


    @GetMapping("/carousels/{id}")
    @ApiOperation("获取单条轮播图信息")
    public Result<Carousel> getCarouselById (@PathVariable("id") Integer id, @TokenToAdminUser AdminUser adminUser){
       Carousel carousel =  mallCarouselService.getCarouselById(id);
        if (carousel != null){
            return ResultGenerator.genSuccessResult(carousel);
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DATA_NOT_EXIST.getResult());
    }


    @PutMapping("/carousels")
    @ApiOperation("修改轮播图信息")
    public Result<String> update (@RequestBody CarouselEditParam carouselEditParam , @TokenToAdminUser AdminUser adminUser){

        Carousel carousel = new Carousel();
        BeanUtil.copyProperties(carouselEditParam ,carousel);
        String result = mallCarouselService.updateCarousel(carousel);
        if (result.equals(ServiceResultEnum.SUCCESS.getResult()))
        {
            return ResultGenerator.genSuccessResult(ServiceResultEnum.SUCCESS.getResult());

        }
        return ResultGenerator.genFailResult(result);

    }



    @DeleteMapping("/carousels")
    @ApiOperation("批量删除轮播图信息")
    public Result<String> delete (@RequestBody BatchIdParam batchIdParam, @TokenToAdminUser AdminUser adminUser){

        if (batchIdParam == null || batchIdParam.getIds().length<1){
            return ResultGenerator.genFailResult(ServiceResultEnum.PARAM_ERROR.getResult());
        }

        if (mallCarouselService.deleteBatch(batchIdParam.getIds())){
            return ResultGenerator.genSuccessResult();
        };
        return ResultGenerator.genFailResult(ServiceResultEnum.DELETE_ERROR.getResult());
    }







}
