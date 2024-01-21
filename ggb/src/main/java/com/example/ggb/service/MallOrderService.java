
package com.example.ggb.service;


import com.example.ggb.controller.mall.vo.MallOrderDetailVO;
import com.example.ggb.controller.mall.vo.MallShoppingCartItemVO;
import com.example.ggb.entity.MallUser;
import com.example.ggb.entity.MallUserAddress;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;

import java.util.List;

public interface MallOrderService {


    String saveOrder(MallUser loginMallUser, MallUserAddress address, List<MallShoppingCartItemVO> itemsForSave);
//模拟支付接口
    String paySuccess(String orderNo, int payType);

    /**
     * 获取订单详情
     *
     * @param orderNo
     * @param userId
     * @return
     */
  MallOrderDetailVO getOrderDetailByOrderNo(String orderNo, Long userId);


    /**
     * 我的订单列表
     *
     * @param pageUtil
     * @return
     */
    PageResult getMyOrders(PageQueryUtil pageUtil);


    /**
     * 手动取消订单
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String cancelOrder(String orderNo, Long userId);

    /**
     * 确认收货
     *
     * @param orderNo
     * @param userId
     * @return
     */
    String finishOrder(String orderNo, Long userId);


    /**
     * @param pageQueryUtil
     * @return @return {@code PageResult }
     * @author dulred
     * @date 2023/10/09
     */
    PageResult getMallOrdersPage (PageQueryUtil pageQueryUtil);


    /**
     * @param orderId
     * @return @return {@code MallOrderDetailVO }
     * @author dulred
     * @date 2023/10/09
     */
    MallOrderDetailVO getOrderDetailByOrderId (Long orderId);

    /**
     * @param ids
     * @return @return {@code String }
     * @author dulred
     * @date 2023/10/09
     */
    String  checkDone (Long[] ids);


    /**
     * @param ids
     * @return @return {@code String }
     * @author dulred
     * @date 2023/10/09
     */
    String checkOut  (Long[] ids);

    /**
     * @param ids
     * @return @return {@code String }
     * @author dulred
     * @date 2023/10/09
     */
    String  closeOrder  (Long[] ids);


}
