package com.example.ggb.repository;

import com.example.ggb.entity.MallGoods;
import com.example.ggb.entity.StockNumDTO;
import com.example.ggb.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsMapper {
    /**
     * @param goodsIds
     * @return @return {@code List<MallGoods> }
     * @author dulred
     * @date 2023/10/09
     */
    List<MallGoods> selectByPrimaryKeys(List<Long> goodsIds);

    List<MallGoods> findMallGoodsListBySearch(PageQueryUtil pageUtil);

    int getTotalMallGoodsBySearch(PageQueryUtil pageUtil);

    MallGoods selectByPrimaryKey(Long goodsId);

    int updateStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    int recoverStockNum(@Param("stockNumDTOS") List<StockNumDTO> stockNumDTOS);

    MallGoods selectByCategoryIdAndName (String goodsName, Long goodsCategoryId);

    int  insertSelective (MallGoods mallGoods);

    int updateByPrimaryKeySelective (MallGoods mallGoods);

    int  batchUpdateSellStatus ( @Param("orderIds")  Long [] ids , int sellStatus);

   List<MallGoods>  findNewBeeMallGoodsList (PageQueryUtil pageQueryUtil);
   int  getTotalNewBeeMallGoods (PageQueryUtil pageQueryUtil);


}
