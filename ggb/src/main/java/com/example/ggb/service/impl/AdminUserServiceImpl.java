
package com.example.ggb.service.impl;

import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.entity.AdminUser;
import com.example.ggb.entity.AdminUserToken;
import com.example.ggb.repository.AdminUserMapper;
import com.example.ggb.repository.AdminUserTokenMapper;
import com.example.ggb.service.AdminUserService;
import com.example.ggb.util.NumberUtil;
import com.example.ggb.util.SystemUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AdminUserServiceImpl implements AdminUserService {

    @Resource
    private AdminUserMapper adminUserMapper;

    @Resource
    private AdminUserTokenMapper adminUserTokenMapper;

    @Override
    public String login(String userName, String password) {
      AdminUser loginAdminUser = adminUserMapper.login(userName, password);
      if (loginAdminUser != null){
          //登录后即执行修改token的操作
          String token  = getNewToken(System.currentTimeMillis() + "" , loginAdminUser.getAdminUserId());
          AdminUserToken adminUserToken = adminUserTokenMapper.selectByPrimaryKey(loginAdminUser.getAdminUserId());
//          当前时间
          Date now  = new Date();
//          过期时间
          Date expireTime = new Date(now.getTime() + 2*24*3600*1000); //过期时间48小时
          if (adminUserToken == null) {
              adminUserToken = new AdminUserToken();
              adminUserToken.setAdminUserId(loginAdminUser.getAdminUserId());
              adminUserToken.setToken(token);
              adminUserToken.setUpdateTime(now);
              adminUserToken.setExpireTime(expireTime);
              //新增一条token数据
              if (adminUserTokenMapper.insertSelective(adminUserToken)>0){
                  //新增成功后返回
                  return token;
              }

          }
          else {
              adminUserToken.setToken(token);
              adminUserToken.setUpdateTime(now);
              adminUserToken.setExpireTime(expireTime);
              if (adminUserTokenMapper.updateByPrimaryKeySelective(adminUserToken)>0){
                  //修改成功后返回
                  return  token;
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
     * @return
     */
    private String getNewToken(String timeStr, Long userId) {
        String src = timeStr + userId + NumberUtil.genRandomNum(6);
        return SystemUtil.genToken(src);
    }

    @Override
    public Boolean updatePassword( AdminUser adminUser) {
       if (adminUserMapper.updateByPrimaryKeySelective(adminUser)>0  && adminUserTokenMapper.deleteByPrimaryKey(adminUser.getAdminUserId()) > 0){
           return true;
       }
        return false;
    }

    @Override
    public Boolean updateName(Long loginUserId, String loginUserName, String nickName) {
        AdminUser adminUser = adminUserMapper.selectByPrimaryKey(loginUserId);
        //当前用户非空才可以进行更改
        if (adminUser != null) {
            //设置新名称并修改
            adminUser.setLoginUserName(loginUserName);
            adminUser.setNickName(nickName);
            if (adminUserMapper.updateByPrimaryKeySelective(adminUser) > 0) {
                //修改成功则返回true
                return true;
            }
        }
        return false;
    }


    @Override
    public Boolean logout(Long adminUserId) {

        return adminUserTokenMapper.deleteByPrimaryKey(adminUserId) > 0;

    }


}
