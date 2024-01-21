package com.example.ggb.controller.admin;

import com.example.ggb.common.Constants;
import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.config.annotation.TokenToAdminUser;
import com.example.ggb.controller.admin.param.BatchIdParam;
import com.example.ggb.controller.admin.param.GoodsAddParam;
import com.example.ggb.controller.admin.param.GoodsEditParam;
import com.example.ggb.entity.AdminUser;
import com.example.ggb.entity.GoodsCategory;
import com.example.ggb.entity.MallGoods;
import com.example.ggb.service.MallCategoryService;
import com.example.ggb.service.MallGoodsService;
import com.example.ggb.util.BeanUtil;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.Result;
import com.example.ggb.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
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
@Api(value = "v1",tags = "后台管理系统商品模块接口")
@RequestMapping("/manage-api/v1")
public class AdminGoodsInfoAPI {

    private static final Logger logger = LoggerFactory.getLogger(AdminGoodsCategoryAPI.class);


    @Resource
    private MallGoodsService mallGoodsService;
    @Resource
    private MallCategoryService mallCategoryService;

    /**
     * 列表
     */
    @GetMapping("/goods/list")
    @ApiOperation("获取商品分页列表")
    public Result getGoodsInfoList  (@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                                     @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                                     @RequestParam(required = false) @ApiParam(value = "商品名称") String goodsName,
                                     @RequestParam(required = false) @ApiParam(value = "上架状态 0-上架 1-下架") Integer goodsSellStatus, @TokenToAdminUser AdminUser adminUser){


        logger.info("adminUser:{}", adminUser.toString());
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10) {
            return ResultGenerator.genFailResult("分页参数异常！");
        }
        Map params = new HashMap(8);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        if (StringUtils.hasText(goodsName)) {
            params.put("goodsName", goodsName);
        }
        if (goodsSellStatus != null) {
            params.put("goodsSellStatus", goodsSellStatus);
        }
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(mallGoodsService.getMallGoodsPage(pageUtil));

    }


    /**
     * 详情
     */
    @GetMapping("/goods/{id}")
    @ApiOperation("获取单条商品信息")
    public Result info(@PathVariable("id") Long id, @TokenToAdminUser AdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        Map goodsInfo = new HashMap(8);
        MallGoods goods = mallGoodsService.getMallGoodsById(id);
        goodsInfo.put("goods", goods);
        GoodsCategory thirdCategory;
        GoodsCategory secondCategory;
        GoodsCategory firstCategory;
        thirdCategory = mallCategoryService.getGoodsCategoryById(goods.getGoodsCategoryId());
        if (thirdCategory != null) {
            goodsInfo.put("thirdCategory", thirdCategory);
            secondCategory = mallCategoryService.getGoodsCategoryById(thirdCategory.getParentId());
            if (secondCategory != null) {
                goodsInfo.put("secondCategory", secondCategory);
                firstCategory = mallCategoryService.getGoodsCategoryById(secondCategory.getParentId());
                if (firstCategory != null) {
                    goodsInfo.put("firstCategory", firstCategory);
                }
            }
        }
        return ResultGenerator.genSuccessResult(goodsInfo);
    }


    /**
     * 添加
     */
    @PostMapping(value = "/goods")
    @ApiOperation(value = "新增商品信息", notes = "新增商品信息")
    public Result save(@RequestBody @Valid GoodsAddParam goodsAddParam, @TokenToAdminUser AdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        MallGoods mallGoods = new MallGoods();
        BeanUtil.copyProperties(goodsAddParam, mallGoods);
        String result = mallGoodsService.saveMallGoods(mallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }



    /**
     * 修改
     */
    @PutMapping(value = "/goods")
    @ApiOperation(value = "修改商品信息", notes = "修改商品信息")
    public Result update(@RequestBody @Valid GoodsEditParam goodsEditParam, @TokenToAdminUser AdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        MallGoods mallGoods = new MallGoods();
        BeanUtil.copyProperties(goodsEditParam, mallGoods);
        String result = mallGoodsService.updateMallGoods(mallGoods);
        if (ServiceResultEnum.SUCCESS.getResult().equals(result)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(result);
        }
    }


    /**
     * 批量修改销售状态
     */
    @PutMapping(value = "/goods/status/{sellStatus}")
    @ApiOperation(value = "批量修改销售状态", notes = "批量修改销售状态")
    public Result delete(@RequestBody BatchIdParam batchIdParam, @PathVariable("sellStatus") int sellStatus, @TokenToAdminUser AdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (batchIdParam == null || batchIdParam.getIds().length < 1) {
            return ResultGenerator.genFailResult("参数异常！");
        }
        if (sellStatus != Constants.SELL_STATUS_UP && sellStatus != Constants.SELL_STATUS_DOWN) {
            return ResultGenerator.genFailResult("状态异常！");
        }
        if (mallGoodsService.batchUpdateSellStatus(batchIdParam.getIds(), sellStatus)) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult("修改失败");
        }
    }



}
