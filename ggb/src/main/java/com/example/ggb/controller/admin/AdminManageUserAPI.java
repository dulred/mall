package com.example.ggb.controller.admin;

import com.example.ggb.common.Constants;
import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.config.annotation.TokenToAdminUser;
import com.example.ggb.controller.admin.param.AdminLoginParam;
import com.example.ggb.controller.admin.param.UpdateAdminNameParam;
import com.example.ggb.controller.admin.param.UpdateAdminPasswordParam;
import com.example.ggb.controller.admin.vo.AdminUserVO;
import com.example.ggb.entity.AdminUser;
import com.example.ggb.service.AdminUserService;
import com.example.ggb.util.BeanUtil;
import com.example.ggb.util.Result;
import com.example.ggb.util.ResultGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author dulred
 * @description 后台管理系统管理员模块接口
 * @github https://github.com/dulred
 */

@RestController
@Api(value = "v1",tags="后台管理系统管理员模块接口")
@RequestMapping("/manage-api/v1")
public class AdminManageUserAPI {

    @Resource
    AdminUserService adminUserService;

    private static final Logger logger = LoggerFactory.getLogger(AdminManageUserAPI.class);

    @PostMapping("/adminUser/login")
    @ApiOperation(value = "登录")
    public Result<String> login(@RequestBody @Valid AdminLoginParam adminLoginParam) {

        String loginResult = adminUserService.login(adminLoginParam.getUserName(), adminLoginParam.getPasswordSha256());
        logger.info("manage login api,adminName={},loginResult={}", adminLoginParam.getUserName(), loginResult);

        //登录成功
        if (loginResult.length() == Constants.TOKEN_LENGTH) {
            Result result = ResultGenerator.genSuccessResult();
            result.setData(loginResult);
            return result;
        }
        //登录失败
        return ResultGenerator.genFailResult(loginResult);

    }


    @DeleteMapping("/adminUser/logout")
    @ApiOperation(value = "登出")
    public Result<String> logout (@TokenToAdminUser AdminUser adminUser) {
        if (adminUserService.logout(adminUser.getAdminUserId())){
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.LOGOUT_ERROR.getResult());
    }


    @GetMapping("/adminUser/profile")
    @ApiOperation(value = "查询信息")
    public Result<AdminUserVO> getUserDetailById (@TokenToAdminUser AdminUser adminUser) {
        AdminUserVO aminUserVO = new AdminUserVO();
        System.out.println(adminUser.toString());
        BeanUtil.copyProperties(adminUser, aminUserVO);
        return ResultGenerator.genSuccessResult(aminUserVO);

    }


    @PutMapping("/adminUser/password")
    @ApiOperation(value = "修改密码")
    public Result<String> updatePassword (@RequestBody @Valid UpdateAdminPasswordParam  updateAdminPasswordParam, @TokenToAdminUser AdminUser adminUser ) {

       logger.info(updateAdminPasswordParam.toString());
        if (adminUser.getLoginPassword().equals(updateAdminPasswordParam.getOriginalPassword())){
            adminUser.setLoginPassword(updateAdminPasswordParam.getNewPassword());
            if ( adminUserService.updatePassword(adminUser))
            {
                return ResultGenerator.genSuccessResult();
            }
        }
        return ResultGenerator.genFailResult(ServiceResultEnum.DB_ERROR.getResult());
    }


    @ApiOperation(value = "修改用户名和昵称")
    @RequestMapping(value = "/adminUser/name", method = RequestMethod.PUT)
    public Result nameUpdate(@RequestBody @Valid UpdateAdminNameParam adminNameParam, @TokenToAdminUser AdminUser adminUser) {
        logger.info("adminUser:{}", adminUser.toString());
        if (adminUserService.updateName(adminUser.getAdminUserId(), adminNameParam.getLoginUserName(), adminNameParam.getNickName())) {
            return ResultGenerator.genSuccessResult();
        } else {
            return ResultGenerator.genFailResult(ServiceResultEnum.DB_ERROR.getResult());
        }
    }





}
