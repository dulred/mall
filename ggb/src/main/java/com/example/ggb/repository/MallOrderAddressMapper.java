
package com.example.ggb.repository;


import com.example.ggb.entity.MallOrderAddress;
import org.springframework.stereotype.Repository;

@Repository
public interface MallOrderAddressMapper {

    int insertSelective(MallOrderAddress record);


}