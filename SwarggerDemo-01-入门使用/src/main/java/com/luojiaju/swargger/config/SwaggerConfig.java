package com.luojiaju.swargger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {


    Contact contact = new Contact("姓名", "http://jilijili.fun", "邮箱");

    @Bean
    public Docket Docket3() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("开发人C")
                ;

    }
    @Bean
    public Docket Docket2() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("开发人B")
                ;

    }

    @Bean
    public Docket Docket(Environment environment) {


        Profiles of = Profiles.of("test", "dev");

        boolean flag = environment.acceptsProfiles(of);

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .groupName("开发人A")
                .enable(flag)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.luojiaju.swargger.controller"))
                .build()
                ;

    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "Api 标题",
                "Api 描述",
                "1.0 版本",
                "urn:tos 组织",
                contact,
                "Apache 2.0 开源版本号",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());


    }

}
