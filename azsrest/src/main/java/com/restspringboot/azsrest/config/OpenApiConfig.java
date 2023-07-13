package com.restspringboot.azsrest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {
 
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
        .info(new Info()
        .title("Rest API with SpringBoot")
        .version("v1")
        .description("Rest API with SpringBoot")
        .termsOfService("https://github.com/CaioAzs")
        .license(new License().name("Apache 2.0").url("https://github.com/CaioAzs"))
        );
    }

    // http://localhost:8080/swagger-ui/index.html#/
}
