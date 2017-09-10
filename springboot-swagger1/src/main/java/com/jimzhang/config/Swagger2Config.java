package com.jimzhang.config;

import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Created by admin on 2017/9/7.
 */
@Configuration // 让Spring来加载该类配置
@EnableSwagger2 // 启用Swagger2
public class Swagger2Config {

    /**
     * 通用配置 生成离线文档不可缺
     * @return
     */
    @Bean
    public Docket configSpringfoxDocket_all() {
        return new Docket(DocumentationType.SWAGGER_2)
                .produces(Sets.newHashSet("application/json"))
                .consumes(Sets.newHashSet("application/json"))
                .protocols(Sets.newHashSet("http", "https"))
                .apiInfo(apiInfo())
                .forCodeGeneration(true)
                .select()
                .paths(regex("/api.*"))
                .build();
    }

    /**
     * 针对user的配置
     * @return
     */
    @Bean
    public Docket createUserRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("user")
                .produces(Sets.newHashSet("application/json"))
                .consumes(Sets.newHashSet("application/json"))
                .protocols(Sets.newHashSet("http", "https"))
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.jimzhang.web")) //扫描指定包下的Controller,并生成文档 （除了被@ApiIgnore指定的请求）
                .paths(regex("/api/users.*"))
//                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建基本信息，会展示在文档首页
     * @return
     */
    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("欢迎关注我的博客：http://www.jianshu.com/u/450bfbaff4e3")
                .termsOfServiceUrl("http://www.jianshu.com/u/450bfbaff4e3")
                .contact(new Contact("Spring官网 ", "https://spring.io/projects", "1539745948@qq.com"))
                .version("1.0").build();
    }
}
