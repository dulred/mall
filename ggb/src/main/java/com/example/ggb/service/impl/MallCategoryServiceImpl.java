package com.example.ggb.service.impl;

import com.example.ggb.common.Constants;
import com.example.ggb.common.MallCategoryLevelEnum;
import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.controller.mall.vo.MallIndexCategoryVO;
import com.example.ggb.controller.mall.vo.SecondLevelCategoryVO;
import com.example.ggb.controller.mall.vo.ThirdLevelCategoryVO;
import com.example.ggb.entity.GoodsCategory;
import com.example.ggb.repository.GoodsCategoryMapper;
import com.example.ggb.service.MallCategoryService;
import com.example.ggb.util.BeanUtil;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
public class MallCategoryServiceImpl implements MallCategoryService {

    @Autowired
    GoodsCategoryMapper goodsCategoryMapper;
    @Override
    public List<MallIndexCategoryVO> getCategoriesForIndex() {
        List<MallIndexCategoryVO> MallIndexCategoryVOS = new ArrayList<>();
        //获取一级分类的固定数量的数据
        List<GoodsCategory> firstLevelCategories = goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(Collections.singletonList(0L), MallCategoryLevelEnum.LEVEL_ONE.getLevel(), Constants.INDEX_CATEGORY_NUMBER);
        if (!CollectionUtils.isEmpty(firstLevelCategories)) {
            List<Long> firstLevelCategoryIds = firstLevelCategories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());
            //获取二级分类的数据
            List<GoodsCategory> secondLevelCategories = goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(firstLevelCategoryIds, MallCategoryLevelEnum.LEVEL_TWO.getLevel(), 0);
            if (!CollectionUtils.isEmpty(secondLevelCategories)) {
                List<Long> secondLevelCategoryIds = secondLevelCategories.stream().map(GoodsCategory::getCategoryId).collect(Collectors.toList());
                //获取三级分类的数据
                List<GoodsCategory> thirdLevelCategories = goodsCategoryMapper.selectByLevelAndParentIdsAndNumber(secondLevelCategoryIds, MallCategoryLevelEnum.LEVEL_THREE.getLevel(), 0);
                if (!CollectionUtils.isEmpty(thirdLevelCategories)) {
                    //根据 parentId 将 thirdLevelCategories 分组
                    Map<Long, List<GoodsCategory>> thirdLevelCategoryMap = thirdLevelCategories.stream().collect(groupingBy(GoodsCategory::getParentId));
                    List<SecondLevelCategoryVO> secondLevelCategoryVOS = new ArrayList<>();
                    //处理二级分类
                    for (GoodsCategory secondLevelCategory : secondLevelCategories) {
                        SecondLevelCategoryVO secondLevelCategoryVO = new SecondLevelCategoryVO();
                        BeanUtil.copyProperties(secondLevelCategory, secondLevelCategoryVO);
                        //如果该二级分类下有数据则放入 secondLevelCategoryVOS 对象中
                        if (thirdLevelCategoryMap.containsKey(secondLevelCategory.getCategoryId())) {
                            //根据二级分类的id取出thirdLevelCategoryMap分组中的三级分类list
                            List<GoodsCategory> tempGoodsCategories = thirdLevelCategoryMap.get(secondLevelCategory.getCategoryId());
                            secondLevelCategoryVO.setThirdLevelCategoryVOS((BeanUtil.copyList(tempGoodsCategories, ThirdLevelCategoryVO.class)));
                            secondLevelCategoryVOS.add(secondLevelCategoryVO);
                        }
                    }
                    //处理一级分类
                    if (!CollectionUtils.isEmpty(secondLevelCategoryVOS)) {
                        //根据 parentId 将 thirdLevelCategories 分组
                        Map<Long, List<SecondLevelCategoryVO>> secondLevelCategoryVOMap = secondLevelCategoryVOS.stream().collect(groupingBy(SecondLevelCategoryVO::getParentId));
                        for (GoodsCategory firstCategory : firstLevelCategories) {
                            MallIndexCategoryVO MallIndexCategoryVO = new MallIndexCategoryVO();
                            BeanUtil.copyProperties(firstCategory, MallIndexCategoryVO);
                            //如果该一级分类下有数据则放入 MallIndexCategoryVOS 对象中
                            if (secondLevelCategoryVOMap.containsKey(firstCategory.getCategoryId())) {
                                //根据一级分类的id取出secondLevelCategoryVOMap分组中的二级级分类list
                                List<SecondLevelCategoryVO> tempGoodsCategories = secondLevelCategoryVOMap.get(firstCategory.getCategoryId());
                                MallIndexCategoryVO.setSecondLevelCategoryVOS(tempGoodsCategories);
                                MallIndexCategoryVOS.add(MallIndexCategoryVO);
                            }
                        }
                    }
                }
            }
            return MallIndexCategoryVOS;
        } else {
            return null;
        }
    }

    @Override
    public PageResult getCategorisPage(PageQueryUtil pageQueryUtil) {
        List<GoodsCategory> goodsCategories = goodsCategoryMapper.findGoodsCategoryList(pageQueryUtil);
        int total = goodsCategoryMapper.getTotalGoodsCategories(pageQueryUtil);
        PageResult pageResult = new PageResult(goodsCategories, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }

    @Override
    public GoodsCategory getGoodsCategoryById(Long categoryId) {
        return  goodsCategoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public String saveCategory(GoodsCategory category) {
        GoodsCategory temp = goodsCategoryMapper.selectByLevelAndName(category.getCategoryLevel(), category.getCategoryName());
        if (temp != null) {
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }
        if (goodsCategoryMapper.insertSelective(category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String updateCategory(GoodsCategory category) {
        GoodsCategory temp = goodsCategoryMapper.selectByPrimaryKey(category.getCategoryId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        GoodsCategory temp2 = goodsCategoryMapper.selectByLevelAndName(category.getCategoryLevel(), category.getCategoryName());
        if (temp2 != null && !temp2.getCategoryId().equals(category.getCategoryId())) {
            //同名且不同id 不能继续修改
            return ServiceResultEnum.SAME_CATEGORY_EXIST.getResult();
        }
        category.setUpdateTime(new Date());
        if (goodsCategoryMapper.updateByPrimaryKeySelective(category) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Boolean deleteBatch(Long[] ids) {
        return goodsCategoryMapper.deleteBatch(ids)>0;
    }



}
