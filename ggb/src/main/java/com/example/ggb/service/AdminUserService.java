package com.example.ggb.service;


import com.example.ggb.entity.AdminUser;

public interface AdminUserService {

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    String login(String userName, String password);


    /**
     * @param adminUser
     * @return @return {@code Boolean }
     * @author dulred
     * @date 2023/10/06
     */
    Boolean updatePassword(AdminUser adminUser);


    /**
     * 修改当前登录用户的名称信息
     *
     * @param loginUserId
     * @param loginUserName
     * @param nickName
     * @return
     */
    Boolean updateName(Long loginUserId, String loginUserName, String nickName);

    /**
     * 登出接口
     * @param adminUserId
     * @return
     */
    Boolean logout(Long adminUserId);







}
