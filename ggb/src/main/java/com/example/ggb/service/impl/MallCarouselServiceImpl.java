package com.example.ggb.service.impl;

import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.controller.mall.vo.MallIndexCarouselVO;
import com.example.ggb.entity.Carousel;
import com.example.ggb.repository.CarouselMapper;
import com.example.ggb.service.MallCarouselService;
import com.example.ggb.util.BeanUtil;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MallCarouselServiceImpl implements MallCarouselService {

    @Autowired
    CarouselMapper carouselMapper;
    @Override
    public List<MallIndexCarouselVO> getCarouselsForIndex(int number) {
        List<MallIndexCarouselVO> mallIndexCarouselVOS = new ArrayList<>(number);
        List<Carousel> carousels = carouselMapper.findCarouselsByNum(number);

        if (!CollectionUtils.isEmpty(carousels)){
            mallIndexCarouselVOS = BeanUtil.copyList(carousels,MallIndexCarouselVO.class);
        }

        return mallIndexCarouselVOS;
    }

    @Override
    public Boolean saveCarousel (Carousel carousel) {
        if (carouselMapper.insertSelective(carousel) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Carousel getCarouselById(Integer id) {
        return carouselMapper.selectByPrimaryKey(id);
    }


    @Override
    public PageResult getCarouselPage(PageQueryUtil pageUtil) {
        List<Carousel> carousels = carouselMapper.findCarouselList(pageUtil);
        int total = carouselMapper.getTotalCarousels(pageUtil);
        PageResult pageResult = new PageResult(carousels, total, pageUtil.getLimit(), pageUtil.getPage());
        return pageResult;
    }

    @Override
    public String updateCarousel(Carousel carousel) {

        Carousel temp =  carouselMapper.selectByPrimaryKey(carousel.getCarouselId());
        if (temp == null){
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        temp.setCarouselUrl(carousel.getCarouselUrl());
        temp.setRedirectUrl(carousel.getRedirectUrl());
        temp.setCarouselRank(carousel.getCarouselRank());
        temp.setUpdateTime(new Date());
        if (carouselMapper.updateByPrimaryKeySelective(temp) >0)
        {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public Boolean deleteBatch(Long[] ids) {


        return carouselMapper.deleteBatch(ids)>0;
    }





}
