package com.example.ggb.controller.admin;

import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.config.annotation.TokenToAdminUser;
import com.example.ggb.controller.admin.param.BatchIdParam;
import com.example.ggb.controller.admin.param.GoodesCategoryEditParam;
import com.example.ggb.controller.admin.param.GoodsCategoryAddParam;
import com.example.ggb.entity.AdminUser;
import com.example.ggb.entity.GoodsCategory;
import com.example.ggb.service.MallCategoryService;
import com.example.ggb.util.BeanUtil;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.Result;
import com.example.ggb.util.ResultGenerator;
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
@Api(value = "v1",tags = "后台管理系统分类模块接口")
@RequestMapping("/manage-api/v1")
public class AdminGoodsCategoryAPI {

    private static final Logger logger = LoggerFactory.getLogger(AdminGoodsCategoryAPI.class);


    @Resource
    MallCategoryService mallCategoryService;

    /**
     * 列表
     */
    @GetMapping( "/categories")
    @ApiOperation(value = "商品分类列表", notes = "根据级别和上级分类id查询")
    public Result list(@RequestParam(required = false) @ApiParam(value = "页码") Integer pageNumber,
                       @RequestParam(required = false) @ApiParam(value = "每页条数") Integer pageSize,
                       @RequestParam(required = false) @ApiParam(value = "分类级别") Integer categoryLevel,
                       @RequestParam(required = false) @ApiParam(value = "上级分类的id") Long parentId, @TokenToAdminUser AdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (pageNumber == null || pageNumber < 1 || pageSize == null || pageSize < 10 || categoryLevel == null || categoryLevel < 0 || categoryLevel > 3 || parentId == null || parentId < 0) {
            return ResultGenerator.genFailResult("分页参数异常！");
        }
        Map params = new HashMap(8);
        params.put("page", pageNumber);
        params.put("limit", pageSize);
        params.put("categoryLevel", categoryLevel);
        params.put("parentId", parentId);
        PageQueryUtil pageUtil = new PageQueryUtil(params);
        return ResultGenerator.genSuccessResult(mallCategoryService.getCategorisPage(pageUtil));
    }

    /**
     * 列表
     */
    @GetMapping(value = "/categories4Select")
    @ApiOperation(value = "商品分类列表", notes = "用于三级分类联动效果制作")
    public Result listForSelect (){
        return ResultGenerator.genSuccessResult();
    }

    /**
     * 详情
     */
    @GetMapping(value = "/categories/{id}")
    @ApiOperation(value = "获取单条分类信息", notes = "根据id查询")
    public Result info (@PathVariable("id") Long id,@TokenToAdminUser AdminUser adminUser){
        if (id < 0 || id == null){
            return ResultGenerator.genFailResult("参数错误");
        }
        GoodsCategory goodsCategory =  mallCategoryService.getGoodsCategoryById(id);
        if (goodsCategory == null){
            return ResultGenerator.genFailResult("未查询到数据");
        }
        return ResultGenerator.genSuccessResult(goodsCategory);
    }

    /**
     * 添加
     */
    @PostMapping(value = "/categories")
    @ApiOperation(value = "新增分类", notes = "新增分类")
    public Result<String> save (@RequestBody @Valid GoodsCategoryAddParam goodsCategoryAddParam,@TokenToAdminUser AdminUser adminUser){
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtil.copyProperties(goodsCategoryAddParam,goodsCategory);
        System.out.println(goodsCategoryAddParam.toString());
        String result =mallCategoryService.saveCategory(goodsCategory);
        if (result.equals(ServiceResultEnum.SUCCESS.getResult())){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult(result);
    }
    /**
     * 修改
     */
    @PutMapping(value = "/categories")
    @ApiOperation(value = "修改分类信息", notes = "修改分类信息")
    public Result<String> update (@RequestBody @Valid GoodesCategoryEditParam  goodesCategoryEditParam, @TokenToAdminUser AdminUser adminUser){
        GoodsCategory goodsCategory = new GoodsCategory();
        BeanUtil.copyProperties(goodesCategoryEditParam,goodsCategory);
        String result = mallCategoryService.updateCategory(goodsCategory);
        if (result.equals(ServiceResultEnum.SUCCESS.getResult())){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult(result);
    }


    /**
     * 分类删除
     */
    @DeleteMapping(value = "/categories")
    @ApiOperation(value = "批量删除分类信息", notes = "批量删除分类信息")
    public Result<String> delete (@RequestBody BatchIdParam batchIdParam,@TokenToAdminUser AdminUser adminUser){
        if (batchIdParam.getIds() ==null || batchIdParam.getIds().length<1){
            return ResultGenerator.genFailResult("参数异常");
        }
        if (mallCategoryService.deleteBatch(batchIdParam.getIds())){
            return ResultGenerator.genSuccessResult();
        }

        return ResultGenerator.genFailResult("删除失败");
    }

}
