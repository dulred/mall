package com.example.ggb.service;

import com.example.ggb.controller.mall.vo.MallIndexConfigGoodsVO;
import com.example.ggb.entity.IndexConfig;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;

import java.util.List;

public interface MallIndexConfigService {
    /**
     * 返回固定数量的首页配置商品对象(首页调用)
     *
     * @param number
     * @return
     */
    List<MallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number);


    /**
     * @param pageQueryUtil
     * @return @return {@code PageResult }
     * @author dulred
     * @date 2023/10/09
     */
    PageResult getConfigsPage (PageQueryUtil pageQueryUtil);


    /**
     * @param indexConfig
     * @return @return {@code String }
     * @author dulred
     * @date 2023/10/09
     */
    String  updateIndexConfig (IndexConfig indexConfig);


    /**
     * @param indexConfig
     * @return @return {@code String }
     * @author dulred
     * @date 2023/10/09
     */
    String saveIndexConfig (IndexConfig indexConfig);


    /**
     * @param ids
     * @return @return boolean
     * @author dulred
     * @date 2023/10/09
     */
    Boolean deleteBatch (Long[] ids);

    /**
     * @param id
     * @return @return {@code IndexConfig }
     * @author dulred
     * @date 2023/10/09
     */
    IndexConfig getIndexConfigById(Long id);

}
