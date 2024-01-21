/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.example.ggb.service.impl;

import com.example.ggb.common.MallCategoryLevelEnum;
import com.example.ggb.common.MallException;
import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.controller.mall.vo.MallSearchGoodsVO;
import com.example.ggb.entity.GoodsCategory;
import com.example.ggb.entity.MallGoods;
import com.example.ggb.repository.GoodsCategoryMapper;
import com.example.ggb.repository.GoodsMapper;
import com.example.ggb.service.MallGoodsService;
import com.example.ggb.util.BeanUtil;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MallGoodsServiceImpl implements MallGoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private GoodsCategoryMapper goodsCategoryMapper;

    @Override
    public PageResult searchMallGoods(PageQueryUtil pageUtil) {
        List<MallGoods> goodsList = goodsMapper.findMallGoodsListBySearch(pageUtil);
        int total = goodsMapper.getTotalMallGoodsBySearch(pageUtil);
        List<MallSearchGoodsVO> MallSearchGoodsVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(goodsList)) {
            MallSearchGoodsVOS = BeanUtil.copyList(goodsList, MallSearchGoodsVO.class);
            for (MallSearchGoodsVO MallSearchGoodsVO : MallSearchGoodsVOS) {
                String goodsName = MallSearchGoodsVO.getGoodsName();
                String goodsIntro = MallSearchGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 28) {
                    goodsName = goodsName.substring(0, 28) + "...";
                    MallSearchGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 30) {
                    goodsIntro = goodsIntro.substring(0, 30) + "...";
                    MallSearchGoodsVO.setGoodsIntro(goodsIntro);
                }
            }
        }
        PageResult pageResult = new PageResult(MallSearchGoodsVOS, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public MallGoods getMallGoodsById(Long id) {
        MallGoods mallGoods = goodsMapper.selectByPrimaryKey(id);
        if (mallGoods == null) {
           MallException.fail(ServiceResultEnum.GOODS_NOT_EXIST.getResult());
        }
        return mallGoods;
    }

    @Override
    public String saveMallGoods(MallGoods mallGood) {

        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(mallGood.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != MallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        if (goodsMapper.selectByCategoryIdAndName(mallGood.getGoodsName(), mallGood.getGoodsCategoryId()) != null) {
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        if (goodsMapper.insertSelective(mallGood) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();

    }

    @Override
    public String updateMallGoods(MallGoods mallGood) {
        GoodsCategory goodsCategory = goodsCategoryMapper.selectByPrimaryKey(mallGood.getGoodsCategoryId());
        // 分类不存在或者不是三级分类，则该参数字段异常
        if (goodsCategory == null || goodsCategory.getCategoryLevel().intValue() != MallCategoryLevelEnum.LEVEL_THREE.getLevel()) {
            return ServiceResultEnum.GOODS_CATEGORY_ERROR.getResult();
        }
        MallGoods temp = goodsMapper.selectByPrimaryKey(mallGood.getGoodsId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        MallGoods temp2 = goodsMapper.selectByCategoryIdAndName(mallGood.getGoodsName(), mallGood.getGoodsCategoryId());
        if (temp2 != null && !temp2.getGoodsId().equals(mallGood.getGoodsId())) {
            //name和分类id相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_GOODS_EXIST.getResult();
        }
        mallGood.setUpdateTime(new Date());
        if (goodsMapper.updateByPrimaryKeySelective(mallGood) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Boolean batchUpdateSellStatus(Long[] ids, int sellStatus) {
        return goodsMapper.batchUpdateSellStatus(ids, sellStatus) > 0;
    }

    @Override
    public PageResult getMallGoodsPage(PageQueryUtil pageQueryUtil) {
        List<MallGoods> goodsList = goodsMapper.findNewBeeMallGoodsList(pageQueryUtil);
        int total = goodsMapper.getTotalNewBeeMallGoods(pageQueryUtil);
        PageResult pageResult = new PageResult(goodsList, total, pageQueryUtil.getLimit(), pageQueryUtil.getPage());
        return pageResult;
    }


}
