package com.example.ggb.repository;

import com.example.ggb.entity.IndexConfig;
import com.example.ggb.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndexConfigMapper {

    /**
     * @param configType
     * @param number
     * @return @return {@code List<IndexConfig> }
     * @author dulred
     * @date 2023/10/09
     */
    List<IndexConfig> findIndexConfigsByTypeAndNum(@Param("configType") int configType, @Param("number") int number);


    /**
     * @param pageQueryUtil
     * @return @return {@code List<IndexConfig> }
     * @author dulred
     * @date 2023/10/09
     */
    List<IndexConfig> findIndexConfigList (PageQueryUtil pageQueryUtil);


    /**
     * @param pageQueryUtil
     * @return @return int
     * @author dulred
     * @date 2023/10/09
     */
    int  getTotalIndexConfigs (PageQueryUtil pageQueryUtil);

    /**
     * @param configType
     * @param goodsId
     * @return @return {@code IndexConfig }
     * @author dulred
     * @date 2023/10/09
     */
    IndexConfig selectByTypeAndGoodsId(@Param("configType") int configType, @Param("goodsId") Long goodsId);

    /**
     * @param record
     * @return @return int
     * @author dulred
     * @date 2023/10/09
     */
    int insertSelective(IndexConfig record);


    /**
     * @param ids
     * @return @return int
     * @author dulred
     * @date 2023/10/09
     */
    int deleteBatch(Long[] ids);


    /**
     * @param record
     * @return @return int
     * @author dulred
     * @date 2023/10/09
     */
    int updateByPrimaryKeySelective(IndexConfig record);


    /**
     * @param configId
     * @return @return {@code IndexConfig }
     * @author dulred
     * @date 2023/10/09
     */
    IndexConfig selectByPrimaryKey(Long configId);


}
