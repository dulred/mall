package com.example.ggb.service.impl;

import com.example.ggb.common.Constants;
import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.controller.mall.param.MallUserUpdateParam;
import com.example.ggb.entity.MallUser;
import com.example.ggb.entity.MallUserToken;
import com.example.ggb.repository.MallUserMapper;
import com.example.ggb.repository.MallUserTokenMapper;
import com.example.ggb.service.MallUserService;
import com.example.ggb.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MallUserServiceImpl  implements MallUserService {
    @Autowired
    MallUserMapper mallUserMapper;
    @Autowired
    MallUserTokenMapper mallUserTokenMapper;


    /**
     * 登录
     *
     * @param loginName      登录名
     * @param passwordSha256 密码sha256
     * @return @return {@code String }
     * @author dulred
     * @date 2023/08/29
     */
    @Override
    public String login(String loginName, String passwordSha256) {

        MallUser user  = mallUserMapper.selectByLoginNameAndPasswd(loginName, passwordSha256);
        if (user != null) {

            if (user.getLockedFlag() == 1){
                return ServiceResultEnum.LOGIN_USER_LOCKED_ERROR.getResult();
            }
// 登录后即执行修改token的操作
            MallUserToken mallUserToken  = mallUserTokenMapper.selectByPrimaryKey(user.getUserId());

            String token = genToken(System.currentTimeMillis()+ "",user.getUserId());
            Date now = new Date();
            Date expireTime = new Date(now.getTime() + 24*2*3600*1000);

            if (mallUserToken == null) {
                mallUserToken = new MallUserToken();
                mallUserToken.setUserId(user.getUserId());
                mallUserToken.setToken(token);
                mallUserToken.setExpireTime(expireTime);
                mallUserToken.setUpdateTime(now);
//                添加token记录后返回
                if (mallUserTokenMapper.insertSelective(mallUserToken)> 0) {
                    return token;
                }



            } else{
                mallUserToken.setToken(token);
                mallUserToken.setExpireTime(expireTime);
                mallUserToken.setUpdateTime(now);
//                添加token记录后返回
                if (mallUserTokenMapper.updateByPrimaryKeySelective(mallUserToken)> 0) {
                    return token;
                }
            }



        }


        return ServiceResultEnum.LOGIN_ERROR.getResult();
    }


    /**
     * 获取token值
     *
     * @param timeStr
     * @param userId
     * @return @return {@code String }
     * @author dulred
     * @date 2023/08/29
     */
    private String genToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(4);
        return SystemUtil.genToken(src);
    }

    /**
     * 注册
     *
     * @param loginName      登录名
     * @param password      密码sha256
     * @return @return {@code String }
     * @author dulred
     * @date 2023/08/31
     */
    @Override
    public String register(String loginName, String password) {
        if (mallUserMapper.selectByLoginName(loginName) != null) {
            return ServiceResultEnum.SAME_LOGIN_NAME_EXIST.getResult();
        }
        MallUser registerUser  = mallUserMapper.selectByLoginName(loginName);
        registerUser = new MallUser();
        registerUser.setPasswordSha256(password);
        registerUser.setIntroduceSign(Constants.USER_INTRO);
        registerUser.setLoginName(loginName);
        registerUser.setNickName(loginName);
        if(mallUserMapper.insertSelective(registerUser) > 0){
            return ServiceResultEnum.SUCCESS.getResult();
        }
        
        return ServiceResultEnum.DB_ERROR.getResult();
        
    }


    /**
     * 修改用户信息
     *
     * @param mallUser      传入的参数
     * @param userId        userId
     * @return @return {@code Boolean }
     * @author dulred
     * @date 2023/08/31
     */
    @Override
    public Boolean updateUserInfo(MallUserUpdateParam mallUser, Long userId) {

        MallUser user = new MallUser();
        user.setUserId(userId);
        user.setIntroduceSign(mallUser.getIntroduceSign());
        user.setNickName(mallUser.getNickName());
        if (!SHA256Util.SHA256Encode("").equals(mallUser.getPasswordSha256())){
            user.setPasswordSha256(mallUser.getPasswordSha256());
        }
   
        if(mallUserMapper.updateByPrimaryKeySelective(user)>0){
            return true;
        }
        return false;
    }

    /**
     * 登出用户
     *
     * 
     * @param userId        userId
     * @return @return {@code Boolean }
     * @author dulred
     * @date 2023/08/31
     */
    @Override
    public Boolean logout(Long userId) {
        return mallUserTokenMapper.deleteByPrimaryKey(userId)>0;
    }


    @Override
    public Boolean lockUsers(Long[] ids, int lockStatus) {
        return mallUserMapper.lockUserBatch(ids, lockStatus) > 0;
    }

    @Override
    public PageResult getMallUsersPage(PageQueryUtil pageQueryUt) {
        List<MallUser> mallUsers = mallUserMapper.findMallUserList(pageQueryUt);
        int total = mallUserMapper.getTotalMallUsers(pageQueryUt);
        PageResult pageResult = new PageResult(mallUsers, total, pageQueryUt.getLimit(), pageQueryUt.getPage());
        return pageResult;
    }


}
