package com.example.ggb.service;

import com.example.ggb.controller.mall.param.MallUserUpdateParam;
import com.example.ggb.util.PageQueryUtil;
import com.example.ggb.util.PageResult;

public interface MallUserService {




   
    /**
     * 用户注册
     *
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);


    /**
     * 登录
     *
     * @param loginName
     * @param passwordSha256
     * @return
     */
    String login(String loginName, String passwordSha256);

    /**
     * 用户信息修改
     *
     * @param mallUser
     * @return
     */
    Boolean updateUserInfo(MallUserUpdateParam mallUser, Long userId);

    /**
     * 登出接口
     * @param userId
     * @return
     */
    Boolean logout(Long userId);

    /**
     * 用户禁用与解除禁用(0-未锁定 1-已锁定)
     *
     * @param ids
     * @param lockStatus
     * @return
     */
    Boolean lockUsers(Long[] ids, int lockStatus);

    /**
     * 后台分页
     *
     * @param pageQueryUt
     * @return @return {@code PageResult }
     * @author dulred
     * @date 2023/10/09
     */
    //  PageResult getNewBeeMallUsersPage(PageQueryUtil pageUtil);


 PageResult  getMallUsersPage (PageQueryUtil pageQueryUt);

}
