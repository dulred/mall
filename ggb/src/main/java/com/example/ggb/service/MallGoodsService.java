
package com.example.ggb.service;

import com.example.ggb.entity.MallGoods;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;

public interface MallGoodsService {

    /**
     * 商品搜索
     *
     * @param pageUtil
     * @return
     */
    PageResult searchMallGoods(PageQueryUtil pageUtil);

    /**
     * 获取商品详情
     *
     * @param id
     * @return
     */
   MallGoods getMallGoodsById(Long id);

    /**
     * 商品添加
     * @param mallGood
     * @return @return {@code String }
     * @author dulred
     * @date 2023/10/09
     *///
   String  saveMallGoods (MallGoods mallGood);

    /**
     * 商品修改
     * @param mallGood
     * @return @return {@code String }
     * @author dulred
     * @date 2023/10/09
     */
    String updateMallGoods (MallGoods mallGood);

    /**
     * 批量修改商品状态
     * @param ids
     * @param sellStatus
     * @return @return {@code Boolean }
     * @author dulred
     * @date 2023/10/09
     */
    Boolean  batchUpdateSellStatus (Long [] ids, int sellStatus);

    /**
     * 分頁查詢商品列表
     *
     * @param pageQueryUtil
     * @return @return {@code PageResult }
     * @author dulred
     * @date 2023/10/09
     */
    PageResult   getMallGoodsPage (PageQueryUtil pageQueryUtil);

}
