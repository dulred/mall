package com.example.ggb.repository;

import com.example.ggb.entity.Carousel;
import com.example.ggb.util.PageQueryUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarouselMapper {

    List<Carousel> findCarouselList(PageQueryUtil pageUtil);
    List<Carousel> findCarouselsByNum(@Param("number") int number);

    int insertSelective (Carousel carousel);

    Carousel selectByPrimaryKey(Integer carouselId);

    int updateByPrimaryKeySelective(Carousel carousel);

    int deleteBatch (Long[] ids);

    int getTotalCarousels (PageQueryUtil pageQueryUtil);

}
