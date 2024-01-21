package com.example.ggb.repository;

import com.example.ggb.entity.AdminUserToken;
import org.springframework.stereotype.Repository;

/**
 * @author dulred
 * @description
 * @github https://github.com/dulred
 */

@Repository
public interface AdminUserTokenMapper {

    AdminUserToken  selectByToken (String token);

    AdminUserToken selectByPrimaryKey (Long adminUserId);

    int insertSelective  (AdminUserToken record);

    int updateByPrimaryKeySelective (AdminUserToken record);

    int deleteByPrimaryKey (Long adminUserId);
}
