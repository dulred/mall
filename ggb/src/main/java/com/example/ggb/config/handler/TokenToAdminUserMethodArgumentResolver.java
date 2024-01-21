package com.example.ggb.config.handler;

import com.example.ggb.common.Constants;
import com.example.ggb.common.MallException;
import com.example.ggb.common.ServiceResultEnum;
import com.example.ggb.config.annotation.TokenToAdminUser;
import com.example.ggb.entity.AdminUser;
import com.example.ggb.entity.AdminUserToken;
import com.example.ggb.repository.AdminUserMapper;
import com.example.ggb.repository.AdminUserTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class TokenToAdminUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private AdminUserMapper adminUserMapper;
    @Autowired
    private AdminUserTokenMapper adminUserTokenMapper;

    public TokenToAdminUserMethodArgumentResolver() {
    }

    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(TokenToAdminUser.class)) {
            return true;
        }
        return false;
    }


    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if (parameter.getParameterAnnotation(TokenToAdminUser.class) instanceof TokenToAdminUser) {
            AdminUser adminUser = null;
            String token = webRequest.getHeader("token");
            if (null != token && !"".equals(token) && token.length() == Constants.TOKEN_LENGTH) {
                AdminUserToken adminUserToken = adminUserTokenMapper.selectByToken(token);
                if (adminUserToken == null || adminUserToken.getExpireTime().getTime() <= System.currentTimeMillis()) {
                    MallException.fail(ServiceResultEnum.ADMIN_TOKEN_EXPIRE_ERROR.getResult());

                }
                adminUser = adminUserMapper.selectByPrimaryKey(adminUserToken.getAdminUserId());
                if (adminUser == null) {
                    MallException.fail(ServiceResultEnum.ADMIN_NOT_EXIT.getResult());
                }
                if (adminUser.getLocked().intValue() == 1) {
                    MallException.fail(ServiceResultEnum.ADMIN_USER_LOCKED_ERROR.getResult());
                }
                return adminUser;
            } else {
                MallException.fail(ServiceResultEnum.ADMIN_NOT_LOGIN_ERROR.getResult());
            }
        }
        return null;
    }


    public static byte[] getRequestPostBytes(HttpServletRequest request)
            throws IOException {
        int contentLength = request.getContentLength();
        if (contentLength < 0) {
            return null;
        }
        byte buffer[] = new byte[contentLength];
        for (int i = 0; i < contentLength; ) {
            int readlen = request.getInputStream().read(buffer, i,
                    contentLength - i);
            if (readlen == -1) {
                break;
            }
            i += readlen;
        }
        return buffer;
    }


}
