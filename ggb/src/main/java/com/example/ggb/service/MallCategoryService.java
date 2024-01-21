
package com.example.ggb.service;

import com.example.ggb.controller.mall.vo.MallIndexCategoryVO;
import com.example.ggb.entity.GoodsCategory;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;

import java.util.List;

public interface MallCategoryService {


    /**
     * 返回分类数据(首页调用)
     *
     * @return
     */
    List<MallIndexCategoryVO> getCategoriesForIndex();


   PageResult getCategorisPage (PageQueryUtil pageQueryUtil);

   GoodsCategory getGoodsCategoryById (Long categoryId);

   String saveCategory (GoodsCategory category);

   String updateCategory (GoodsCategory category);


   Boolean deleteBatch (Long[] ids);





}
