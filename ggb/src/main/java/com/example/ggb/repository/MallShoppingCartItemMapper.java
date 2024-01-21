
package com.example.ggb.repository;

import com.example.ggb.entity.MallShoppingCartItem;
import com.example.ggb.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MallShoppingCartItemMapper {


    int insertSelective(MallShoppingCartItem record);

   MallShoppingCartItem selectByUserIdAndGoodsId(@Param("mallUserId") Long mallUserId, @Param("goodsId") Long goodsId);

    int selectCountByUserId(Long mallUserId);

    List<MallShoppingCartItem> selectByUserId(@Param("mallUserId") Long mallUserId, @Param("number") int number);

    List<MallShoppingCartItem> findMyMallCartItems(PageQueryUtil pageUtil);

    int getTotalMyMallCartItems(PageQueryUtil pageUtil);

    MallShoppingCartItem selectByPrimaryKey(Long cartItemId);


    int updateByPrimaryKeySelective(MallShoppingCartItem record);

    int deleteByPrimaryKey(Long cartItemId);

    List<MallShoppingCartItem> selectByUserIdAndCartItemIds(@Param("mallUserId") Long mallUserId, @Param("cartItemIds") List<Long> cartItemIds);

    int deleteBatch(List<Long> ids);


}