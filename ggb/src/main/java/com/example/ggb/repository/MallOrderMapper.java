
package com.example.ggb.repository;


import com.example.ggb.entity.MallOrder;
import com.example.ggb.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MallOrderMapper {

    int insertSelective(MallOrder record);
    MallOrder selectByOrderNo(String orderNo);

    int updateByPrimaryKeySelective(MallOrder record);

    int getTotalMallOrders(PageQueryUtil pageUtil);

    List<MallOrder> findMallOrderList(PageQueryUtil pageUtil);
    int closeOrder(@Param("orderIds") List<Long> orderIds, @Param("orderStatus") int orderStatus);

    List<MallOrder> selectByPrimaryKeys(@Param("orderIds") List<Long> orderIds);
    MallOrder selectByPrimaryKey(Long orderId);

    int checkOut(@Param("orderIds") List<Long> orderIds);


    int checkDone(@Param("orderIds") List<Long> asList);

}