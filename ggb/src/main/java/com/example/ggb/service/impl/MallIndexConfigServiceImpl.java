package com.example.ggb.service.impl;

import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.controller.mall.vo.MallIndexConfigGoodsVO;
import com.example.ggb.entity.IndexConfig;
import com.example.ggb.entity.MallGoods;
import com.example.ggb.repository.GoodsMapper;
import com.example.ggb.repository.IndexConfigMapper;
import com.example.ggb.service.MallIndexConfigService;
import com.example.ggb.util.BeanUtil;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MallIndexConfigServiceImpl implements MallIndexConfigService {

    @Autowired
    IndexConfigMapper indexConfigMapper;

    @Autowired
    GoodsMapper goodsMapper;

@Override
public List<MallIndexConfigGoodsVO> getConfigGoodsesForIndex(int configType, int number) {
    List<MallIndexConfigGoodsVO> MallIndexConfigGoodsVOS = new ArrayList<>(number);
    List<IndexConfig> indexConfigs = indexConfigMapper.findIndexConfigsByTypeAndNum(configType,number);
    if (!CollectionUtils.isEmpty(indexConfigs)){
        //取出所有的goodsId
        List<Long> goodsIds = indexConfigs.stream().map(IndexConfig::getGoodsId).collect(Collectors.toList());
        List<MallGoods>MallGoods = goodsMapper.selectByPrimaryKeys(goodsIds);
        MallIndexConfigGoodsVOS = BeanUtil.copyList(MallGoods,MallIndexConfigGoodsVO.class);
        for(MallIndexConfigGoodsVO mallIndexConfigGoodsVO : MallIndexConfigGoodsVOS ) {

                String goodsName = mallIndexConfigGoodsVO.getGoodsName();
                String goodsIntro = mallIndexConfigGoodsVO.getGoodsIntro();
                // 字符串过长导致文字超出的问题
                if (goodsName.length() > 30) {
                    goodsName = goodsName.substring(0, 30) + "...";
                    mallIndexConfigGoodsVO.setGoodsName(goodsName);
                }
                if (goodsIntro.length() > 22) {
                    goodsIntro = goodsIntro.substring(0, 22) + "...";
                    mallIndexConfigGoodsVO.setGoodsIntro(goodsIntro);
                }

        }


    }


    return MallIndexConfigGoodsVOS;
}

    @Override
    public PageResult getConfigsPage(PageQueryUtil pageQueryUtil) {

      List<IndexConfig> indexConfigs  = indexConfigMapper.findIndexConfigList(pageQueryUtil);
      int total = indexConfigMapper.getTotalIndexConfigs(pageQueryUtil);
      PageResult pageResult = new PageResult(indexConfigs,total,pageQueryUtil.getLimit(),pageQueryUtil.getPage());
      return pageResult;
    }


    @Override
    public String updateIndexConfig(IndexConfig indexConfig) {
        if (goodsMapper.selectByPrimaryKey(indexConfig.getGoodsId()) == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        IndexConfig temp = indexConfigMapper.selectByPrimaryKey(indexConfig.getConfigId());
        if (temp == null) {
            return ServiceResultEnum.DATA_NOT_EXIST.getResult();
        }
        IndexConfig temp2 = indexConfigMapper.selectByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId());
        if (temp2 != null && !temp2.getConfigId().equals(indexConfig.getConfigId())) {
            //goodsId相同且不同id 不能继续修改
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        indexConfig.setUpdateTime(new Date());
        if (indexConfigMapper.updateByPrimaryKeySelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }

    @Override
    public String saveIndexConfig(IndexConfig indexConfig) {
        if (goodsMapper.selectByPrimaryKey(indexConfig.getGoodsId()) == null) {
            return ServiceResultEnum.GOODS_NOT_EXIST.getResult();
        }
        if (indexConfigMapper.selectByTypeAndGoodsId(indexConfig.getConfigType(), indexConfig.getGoodsId()) != null) {
            return ServiceResultEnum.SAME_INDEX_CONFIG_EXIST.getResult();
        }
        if (indexConfigMapper.insertSelective(indexConfig) > 0) {
            return ServiceResultEnum.SUCCESS.getResult();
        }
        return ServiceResultEnum.DB_ERROR.getResult();
    }


    @Override
    public Boolean deleteBatch(Long[] ids) {
        //删除数据
        return indexConfigMapper.deleteBatch(ids) > 0;
    }

    @Override
    public IndexConfig getIndexConfigById(Long id) {
        return indexConfigMapper.selectByPrimaryKey(id);
    }



}
