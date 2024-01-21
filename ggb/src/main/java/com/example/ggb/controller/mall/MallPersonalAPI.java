package com.example.ggb.controller.mall;

import com.example.ggb.common.Constants;
import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.config.annotation.TokenToMallUser;
import com.example.ggb.controller.mall.param.MallUserLoginParam;
import com.example.ggb.service.MallUserService;
import com.example.ggb.util.NumberUtil;
import com.example.ggb.util.Result;
import com.example.ggb.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.example.ggb.util.BeanUtil;

import com.example.ggb.controller.mall.param.MallUserRegisterParam;
import com.example.ggb.controller.mall.param.MallUserUpdateParam;
import com.example.ggb.controller.mall.vo.MallUserVO;
import com.example.ggb.entity.MallUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Api(value = "v1",tags = "商城用户接口")
@RequestMapping(value ="/api/v1")
public class MallPersonalAPI {
    @Autowired
    MallUserService mallUserService;

    private static final Logger logger = LoggerFactory.getLogger(MallPersonalAPI.class);

    /**
     * 登录
     *
     * @param mallUserLoginParam 商城用户登录参数
     * @return @return {@code Result<String> }
     * @author dulred
     * @date 2023/08/27
     */
    @PostMapping("/user/login")
    @ApiOperation("用户登录")
    public Result<String>login(@RequestBody @Valid MallUserLoginParam mallUserLoginParam){
        //  验证手机
        if (!NumberUtil.isPhone(mallUserLoginParam.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }

        String loginResult =mallUserService.login(mallUserLoginParam.getLoginName(),mallUserLoginParam.getPasswordSha256());

        logger.info("login api,loginName={},loginResult={}", mallUserLoginParam.getLoginName(), loginResult);
        //登录成功
        if (StringUtils.hasText(loginResult) && loginResult.length() == Constants.TOKEN_LENGTH ){
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;  
        }

        //登录失败
        return ResultGenerator.genFailResult(loginResult);

    }

     /**
     * 用户注册
     *
     * @param mallUserRegisterParam 商城用户注册参数
     * @return @return {@code Result<String> }
     * @author dulred
     * @date 2023/08/31
     */
    @ApiOperation("用户注册接口")
    @PostMapping("/user/register")
    public Result<String> register (@RequestBody @Valid MallUserRegisterParam mallUserRegisterParam){
        
        if(!NumberUtil.isPhone(mallUserRegisterParam.getLoginName())){
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGIN_NAME_IS_NOT_PHONE.getResult());
        }
        String registerResult = mallUserService.register(mallUserRegisterParam.getLoginName(),mallUserRegisterParam.getPassword());
        
        logger.info("register api,loginName={},loginResult={}", mallUserRegisterParam.getLoginName(), registerResult);

        if(ServiceResultEnum.SUCCESS.getResult().equals(registerResult)){
            return ResultGenerator.genSuccessResult();
        }


        return ResultGenerator.genFailResult(registerResult);

    }


    /**
     * 获取用户信息
     *
     * 
     * @return @return {@code Result<MallUserVO> }
     * @author dulred
     * @date 2023/08/31
     */
    @GetMapping("/user/info")
    @ApiOperation("获取用户信息")
    public Result<MallUserVO> getUserDetail(@TokenToMallUser MallUser loginMallUser){

      MallUserVO mallUserVO = new MallUserVO();
       BeanUtil.copyProperties(loginMallUser, mallUserVO);
       return ResultGenerator.genSuccessResult(mallUserVO);
      
    }



     /**
     * 修改用户信息
     *
     * @param mallUserUpdateParam 商城用户更新参数
     * @return @return {@code Result<String> }
     * @author dulred
     * @date 2023/08/31
     */
    @PutMapping("/user/info")
    @ApiOperation("修改用户信息")
    public Result<String> updateInfo(@RequestBody @Valid MallUserUpdateParam mallUserUpdateParam, @TokenToMallUser MallUser loginMallUser )
    {

        if (mallUserService.updateUserInfo(mallUserUpdateParam, loginMallUser.getUserId())) {
            //修改成功
            return ResultGenerator.genSuccessResult(ServiceResultEnum.UPDATE_SUCCESS.getResult());
        } else {
            //修改失败
            return ResultGenerator.genFailResult(ServiceResultEnum.UPDATE_ERROR.getResult());
        }

    } 

    /**
     * 用户登出
     *
     * 
     * @return @return {@code Result<String> }
     * @author dulred
     * @date 2023/08/31
     */
    @PostMapping("/user/logout")
    @ApiOperation("用户登出")
    public Result<String> logout (@TokenToMallUser MallUser loginMallUser){

        if (mallUserService.logout(loginMallUser.getUserId())) {
            //登出成功
            return ResultGenerator.genSuccessResult(ServiceResultEnum.LOGOUT_SUCCESS.getResult());
        } else {
            //登出失败
            return ResultGenerator.genFailResult(ServiceResultEnum.LOGOUT_ERROR.getResult());
        }
    }







    







}
