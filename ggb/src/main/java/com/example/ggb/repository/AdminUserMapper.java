package com.example.ggb.repository;

import com.example.ggb.entity.AdminUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author dulred
 * @description
 * @github https://github.com/dulred
 */
@Repository
public interface AdminUserMapper {

    AdminUser login(@Param("userName") String userName, @Param("password") String password);

    AdminUser  selectByPrimaryKey (Long adminUserId);

    int updateByPrimaryKeySelective (AdminUser adminUser);


}
