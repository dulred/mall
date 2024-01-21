package com.example.ggb.config;

import com.example.ggb.entity.AdminUser;
import com.example.ggb.entity.MallUser;
import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableOpenApi
@EnableKnife4j
public class Swagger3Config {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .ignoredParameterTypes(MallUser.class, AdminUser.class)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.ggb.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalRequestParameters(getGlobalRequestParameters());
    }

    //生成全局通用参数
    private List<RequestParameter> getGlobalRequestParameters() {
        List<RequestParameter> parameters = new ArrayList<>();
        parameters.add(new RequestParameterBuilder()
                .name("token")
                .description("登录认证token")
                .required(false) // 非必传
                .in(ParameterType.HEADER) //请求头中的参数，其它类型可以点进ParameterType类中查看
                .query(q -> q.model(m -> m.scalarModel(ScalarType.STRING)))
                .build());
        return parameters;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("努努接口文档")
                .description("swagger接口文档")
                .version("3.0")
                .build();
    }

}
