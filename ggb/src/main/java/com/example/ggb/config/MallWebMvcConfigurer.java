/**
 * 严肃声明：
 * 开源版本请务必保留此注释头信息，若删除我方将保留所有法律责任追究！
 * 本软件已申请软件著作权，受国家版权局知识产权以及国家计算机软件著作权保护！
 * 可正常分享和学习源码，不得用于违法犯罪活动，违者必究！
 * Copyright (c) 2019-2021 十三 all rights reserved.
 * 版权所有，侵权必究！
 */
package com.example.ggb.config;

import com.example.ggb.common.Constants;
import com.example.ggb.config.handler.TokenToAdminUserMethodArgumentResolver;
import com.example.ggb.config.handler.TokenToMallUserMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

@Component
public class MallWebMvcConfigurer extends WebMvcConfigurationSupport {


    @Autowired
    private TokenToMallUserMethodArgumentResolver tokenToMallUserMethodArgumentResolver;

    @Autowired
    private TokenToAdminUserMethodArgumentResolver tokenToAdminUserMethodArgumentResolver;
    /**
     * TokenToMallUser 注解处理方法
     *
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tokenToMallUserMethodArgumentResolver);
        argumentResolvers.add(tokenToAdminUserMethodArgumentResolver);
    }

    public void addResourceHandlers(ResourceHandlerRegistry registry) {

//文件上传回显路径
        registry.addResourceHandler("/upload/**").addResourceLocations("file:" + Constants.FILE_UPLOAD_DIC);
        registry.addResourceHandler("/goods-img/**").addResourceLocations("file:" + "D:\\dulred\\learning\\SOC\\upload\\goods-img\\");

//        doc.html
        registry.
                addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/");
// swaager-ui/index.html
        registry.
                addResourceHandler("/swagger-ui/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/springfox-swagger-ui/")
                .resourceChain(false);

    }

    /**
     * 跨域配置
     *
     * @param registry 注冊表
     * @return
     * @author dulred
     * @date 2023/08/27
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true).maxAge(3600);
    }

}
