package com.zh.server.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger配置类
 * @author ZH
 * @date 2021-01-21
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2Config {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.zh.server.controller"))
                .paths(PathSelectors.any())
                .build()
                //为swagger添加全局的登录功能
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    /**
     * http://localhost:8081/api/doc.html 新ui的地址
     * http://localhost:8081/api/swagger.html 原来ui地址
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("云E办接口文档")
                .description("云E办接口描述")
                .contact(new Contact("作者：ZH","http://localhost:8081/api/doc.html","1350777414@qq.com"))
                .version("V1.0.0")
                .build();
    }

    /**
     * 设置请求头
     * @return
     */
    private List<ApiKey> securitySchemes(){
        List<ApiKey> result=new ArrayList<>();
        //第一个参数：ApiKey的名称  第二个参数：头文件里的名称  第三个参数：放的位置
        ApiKey apiKey=new ApiKey("Authorization","Authorization","Header");
        result.add(apiKey);
        return result;
    }

    /**
     * 设置需要认证的路径，使得（Swagger）测试资源受限接口更方便
     * @return
     */
    private List<SecurityContext> securityContexts(){
        List<SecurityContext> result=new ArrayList<>();
        result.add(getContextByPath("/hello/.*"));//注意全部放行的写法
        return  result;
    }

    /**
     * 对路径进行授权，定义作用域、描述
     * @param pathRegex
     * @return
     */
    private SecurityContext getContextByPath(String pathRegex){
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.regex(pathRegex))
                .build();
    }

    /**
     * 定义作用域、描述
     * 作用在指定的头部key上
     * @return
     */
    private List<SecurityReference> defaultAuth(){
        List<SecurityReference> result=new ArrayList<>();
        //定义作用域为全局的（global），描述为accessEverything
        AuthorizationScope authorizationScope=new AuthorizationScope("global","accessEverything");
        AuthorizationScope[] authorizationScopes=new AuthorizationScope[1];

        authorizationScopes[0]=authorizationScope;
        result.add(new SecurityReference("Authorization",authorizationScopes));
        return result;
    }
}
