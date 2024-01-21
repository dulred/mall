package com.example.ggb.service;

import com.example.ggb.controller.mall.param.SaveCartItemParam;
import com.example.ggb.controller.mall.param.UpdateCartItemParam;
import com.example.ggb.controller.mall.vo.MallShoppingCartItemVO;
import com.example.ggb.entity.MallShoppingCartItem;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;

import java.util.List;

public interface MallShoppingCartService {

    /**
     * 保存商品至购物车中
     *
     * @param saveCartItemParam
     * @param userId
     * @return
     */
    String saveMallCartItem(SaveCartItemParam saveCartItemParam, Long userId);


    /**
     * 获取我的购物车中的列表数据
     *
     * @param newBeeMallUserId
     * @return
     */
    List<MallShoppingCartItemVO> getMyShoppingCartItems(Long newBeeMallUserId);


    /**
     * 我的购物车(分页数据)
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyShoppingCartItems(PageQueryUtil pageUtil);



    /**
     * 修改购物车中的属性
     *
     * @param updateCartItemParam
     * @param userId
     * @return
     */
    String updateMallCartItem(UpdateCartItemParam updateCartItemParam, Long userId);


    /**
     * 获取购物项详情
     *
     * @param mallShoppingCartItemId
     * @return
     */
    MallShoppingCartItem getMallCartItemById(Long mallShoppingCartItemId);

    /**
     * 删除购物车中的商品
     *
     *
     * @param shoppingCartItemId
     * @param userId
     * @return
     */
    Boolean deleteById(Long shoppingCartItemId, Long userId);



    /**
     * 根据userId和cartItemIds获取对应的购物项记录
     *
     * @param cartItemIds
     * @param newBeeMallUserId
     * @return
     */
    List<MallShoppingCartItemVO> getCartItemsForSettle(List<Long> cartItemIds, Long newBeeMallUserId);


}
