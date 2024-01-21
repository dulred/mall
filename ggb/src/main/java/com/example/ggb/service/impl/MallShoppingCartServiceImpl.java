package com.example.ggb.service.impl;

import com.example.ggb.common.Constants;
import com.example.ggb.common.MallException;
import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.controller.mall.param.SaveCartItemParam;
import com.example.ggb.controller.mall.param.UpdateCartItemParam;
import com.example.ggb.controller.mall.vo.MallShoppingCartItemVO;
import com.example.ggb.entity.MallGoods;
import com.example.ggb.entity.MallShoppingCartItem;
import com.example.ggb.repository.GoodsMapper;
import com.example.ggb.repository.MallShoppingCartItemMapper;
import com.example.ggb.service.MallShoppingCartService;
import com.example.ggb.util.BeanUtil;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author dulred
 * @description
 * @github https://github.com/dulred
 */
@Service
public class MallShoppingCartServiceImpl  implements MallShoppingCartService {

    @Autowired
    GoodsMapper mallGoodsMapper;
    @Autowired
    MallShoppingCartItemMapper mallShoppingCartItemMapper ;
    @Override
    public String saveMallCartItem(SaveCartItemParam saveCartItemParam, Long userId) {
        MallShoppingCartItem temp = mallShoppingCartItemMapper.selectByUserIdAndGoodsId(userId, saveCartItemParam.getGoodsId());
        if (temp != null) {
            //已存在则修改该记录
            MallException.fail(ServiceResultEnum.SHOPPING_CART_ITEM_EXIST_ERROR.getResult());
        }
        MallGoods mallGoods = mallGoodsMapper.selectByPrimaryKey(saveCartItemParam.getGoodsId());
        //商品为空
        if (mallGoods == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        int totalItem = mallShoppingCartItemMapper.selectCountByUserId(userId);
        //小于单个商品的最小数量
        if (saveCartItemParam.getGoodsCount() < 1) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_NUMBER_ERROR.getResult();
        }
        //超出单个商品的最大数量
        if (saveCartItemParam.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //总的项数超出最大数量
        if (totalItem > Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_TOTAL_NUMBER_ERROR.getResult();
        }
       MallShoppingCartItem mallShoppingCartItem = new MallShoppingCartItem();
        BeanUtil.copyProperties(saveCartItemParam, mallShoppingCartItem);
           mallShoppingCartItem.setUserId(userId);
        //保存记录
        if (mallShoppingCartItemMapper.insertSelective(mallShoppingCartItem) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    /**
     * 数据转换
     *
     * @param mallShoppingCartItemVOS
     * @param mallShoppingCartItems
     * @return
     */
    private List<MallShoppingCartItemVO> getMallShoppingCartItemVOS(List<MallShoppingCartItemVO> mallShoppingCartItemVOS, List<MallShoppingCartItem> mallShoppingCartItems) {
        if (!CollectionUtils.isEmpty(mallShoppingCartItems)) {
            //查询商品信息并做数据转换
            List<Long> mallGoodsIds = mallShoppingCartItems.stream().map(MallShoppingCartItem::getGoodsId).collect(Collectors.toList());
            List<MallGoods> mallGoods = mallGoodsMapper.selectByPrimaryKeys(mallGoodsIds);
            Map<Long, MallGoods> mallGoodsMap = new HashMap<>();
            if (!CollectionUtils.isEmpty(mallGoods)) {
                mallGoodsMap = mallGoods.stream().collect(Collectors.toMap(MallGoods::getGoodsId, Function.identity(), (entity1, entity2) -> entity1));
            }
            for (MallShoppingCartItem mallShoppingCartItem : mallShoppingCartItems) {
                MallShoppingCartItemVO mallShoppingCartItemVO = new MallShoppingCartItemVO();
                BeanUtil.copyProperties(mallShoppingCartItem, mallShoppingCartItemVO);
                if (mallGoodsMap.containsKey(mallShoppingCartItem.getGoodsId())) {
                    MallGoods mallGoodsTemp = mallGoodsMap.get(mallShoppingCartItem.getGoodsId());
                    mallShoppingCartItemVO.setGoodsCoverImg(mallGoodsTemp.getGoodsCoverImg());
                    String goodsName = mallGoodsTemp.getGoodsName();
                    // 字符串过长导致文字超出的问题
                    if (goodsName.length() > 28) {
                        goodsName = goodsName.substring(0, 28) + "...";
                    }
                    mallShoppingCartItemVO.setGoodsName(goodsName);
                    mallShoppingCartItemVO.setSellingPrice(mallGoodsTemp.getSellingPrice());
                    mallShoppingCartItemVOS.add(mallShoppingCartItemVO);
                }
            }
        }
        return mallShoppingCartItemVOS;
    }

    @Override
    public List<MallShoppingCartItemVO> getMyShoppingCartItems(Long mallUserId) {
        List<MallShoppingCartItemVO> mallShoppingCartItemVOS = new ArrayList<>();
        List<MallShoppingCartItem> mallShoppingCartItems = mallShoppingCartItemMapper.selectByUserId(mallUserId, Constants.SHOPPING_CART_ITEM_TOTAL_NUMBER);
        return getMallShoppingCartItemVOS(mallShoppingCartItemVOS, mallShoppingCartItems);
    }


    @Override
    public PageResult getMyShoppingCartItems(PageQueryUtil pageUtil) {
        List<MallShoppingCartItemVO> mallShoppingCartItemVOS = new ArrayList<>();
        List<MallShoppingCartItem> mallShoppingCartItems = mallShoppingCartItemMapper.findMyMallCartItems(pageUtil);
        int total = mallShoppingCartItemMapper.getTotalMyMallCartItems(pageUtil);
        PageResult pageResult = new PageResult(getMallShoppingCartItemVOS(mallShoppingCartItemVOS, mallShoppingCartItems), total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }


    @Override
    public String updateMallCartItem(UpdateCartItemParam updateCartItemParam, Long userId) {
        MallShoppingCartItem mallShoppingCartItemUpdate = mallShoppingCartItemMapper.selectByPrimaryKey(updateCartItemParam.getCartItemId());
        if (mallShoppingCartItemUpdate == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        //超出单个商品的最大数量
        if (updateCartItemParam.getGoodsCount() > Constants.SHOPPING_CART_ITEM_LIMIT_NUMBER) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_LIMIT_NUMBER_ERROR.getResult();
        }
        //超出单个商品的最大数量
        if (updateCartItemParam.getGoodsCount() <= 0) {
            return ServiceResultEnum.SHOPPING_CART_ITEM_NUMBER_ERROR.getResult();
        }
        //当前登录账号的userId与待修改的cartItem中userId不同，返回错误
        if (!mallShoppingCartItemUpdate.getUserId().equals(userId)) {
            return ServiceResultEnum.NO_PERMISSION_ERROR.getResult();
        }
        //数值相同，则不执行数据操作
        if (updateCartItemParam.getGoodsCount().equals(mallShoppingCartItemUpdate.getGoodsCount())) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        mallShoppingCartItemUpdate.setGoodsCount(updateCartItemParam.getGoodsCount());
        mallShoppingCartItemUpdate.setUpdateTime(new Date());
        //修改记录
        if (mallShoppingCartItemMapper.updateByPrimaryKeySelective(mallShoppingCartItemUpdate) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }




    @Override
    public MallShoppingCartItem getMallCartItemById(Long mallShoppingCartItemId) {
        MallShoppingCartItem mallShoppingCartItem =mallShoppingCartItemMapper.selectByPrimaryKey(mallShoppingCartItemId);
        if (mallShoppingCartItem == null) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return mallShoppingCartItem;
    }

    @Override
    public Boolean deleteById(Long shoppingCartItemId, Long userId) {
        MallShoppingCartItem mallShoppingCartItem = mallShoppingCartItemMapper.selectByPrimaryKey(shoppingCartItemId);
        if (mallShoppingCartItem == null) {
            return false;
        }
        //userId不同不能删除
        if (!userId.equals(mallShoppingCartItem.getUserId())) {
            return false;
        }
        return mallShoppingCartItemMapper.deleteByPrimaryKey(shoppingCartItemId) > 0;
    }

    @Override
    public List<MallShoppingCartItemVO> getCartItemsForSettle(List<Long> cartItemIds, Long mallUserId) {

        List<MallShoppingCartItemVO> mallShoppingCartItemVOS = new ArrayList<>();

        List<MallShoppingCartItem> mallShoppingCartItems = mallShoppingCartItemMapper.selectByUserIdAndCartItemIds(mallUserId, cartItemIds);
        if (CollectionUtils.isEmpty(mallShoppingCartItems)) {
            MallException.fail("购物项不能为空");
        }
        if (mallShoppingCartItems.size() != cartItemIds.size()) {
            MallException.fail("参数异常");
        }

        return getMallShoppingCartItemVOS(mallShoppingCartItemVOS, mallShoppingCartItems);
    }




}
