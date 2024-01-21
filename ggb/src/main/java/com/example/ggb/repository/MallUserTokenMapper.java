package com.example.ggb.repository;

import com.example.ggb.entity.MallUserToken;
import org.springframework.stereotype.Repository;

@Repository
public interface MallUserTokenMapper {

    int deleteByPrimaryKey(Long userId);

    int insert(MallUserToken record);

    int insertSelective(MallUserToken record);

    MallUserToken selectByPrimaryKey(Long userId);

    MallUserToken selectByToken(String token);

    int updateByPrimaryKeySelective(MallUserToken record);

    int updateByPrimaryKey(MallUserToken record);
}
