package com.imyuanxiao.rbac.config;

import org.springframework.beans.factory.annotation.Value;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @description Configuration for swagger
 * @author: <a href="https://github.com/imyuanxiao">imyuanxiao</a>
 * <a href="http://localhost:8080/swagger-ui/index.html">swagger</a>
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Value("${packages.controller}")
    private String controllerPackages;

    @Value("${swagger.title}")
    private String swaggerTitle;

    @Value("${swagger.description}")
    private String swaggerDescription;

    @Value("${swagger.version}")
    private String swaggerVersion;

    /**
     * Configures the Swagger documentation for the API.
     * It sets the API title, description, version, and security context.
     * @author imyuanxiao
     **/
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(controllerPackages))
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Collections.singletonList(securityContexts()))
                .securitySchemes(List.of(securitySchemes()))
                .apiInfo(apiInfo());
    }

    /**
     * Configures the Swagger API information such as title, description, and version.
     * @author imyuanxiao
     **/
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerTitle)
                .description(swaggerDescription)
                .version(swaggerVersion)
                .build();
    }

    /**
     * Configures the security scheme used by the API. 
     * It sets the type of authentication and the name of the authorization header.
     * @author imyuanxiao
     **/
    private SecurityScheme securitySchemes() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    /**
     * Configures the security context for the API.
     * @author imyuanxiao
     **/
    private SecurityContext securityContexts() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any())
                .build();
    }
    
    /**
     * Configures the default security reference used by the API.
     * It specifies the authorization scope and references the authorization header defined in the security scheme.
     * @author imyuanxiao
     **/
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("All", "Use all api");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return List.of(new SecurityReference("Authorization", authorizationScopes));
    }

}