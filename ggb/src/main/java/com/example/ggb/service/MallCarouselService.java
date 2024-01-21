package com.example.ggb.service;

import com.example.ggb.controller.mall.vo.MallIndexCarouselVO;
import com.example.ggb.entity.Carousel;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;

import java.util.List;

public interface MallCarouselService {

    /**
     *
     * 返回固定数量的轮播图对象(首页调用)
     *
     * @param number
     * @return @return {@code List<MallIndexCarouselVO> }
     * @author dulred
     * @date 2023/09/02
     */
    List<MallIndexCarouselVO> getCarouselsForIndex(int number);

    /**
     * 插入轮播图
     * @param carousel
     * @return @return {@code Boolean }
     * @author dulred
     * @date 2023/10/08
     */
    Boolean  saveCarousel (Carousel carousel);

    Carousel getCarouselById (Integer id);

   String  updateCarousel (Carousel carousel);

    PageResult getCarouselPage(PageQueryUtil pageUtil);

   Boolean deleteBatch (Long[] ids);

}
