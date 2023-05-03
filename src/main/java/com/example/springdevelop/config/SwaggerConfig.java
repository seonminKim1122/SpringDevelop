package com.example.springdevelop.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v1.0.0")
                .title("HangHae Board")
                .description("API Description");

        // SecurityScheme name
        String auth_header = "jwtAuth";

        // API 요청헤더에 인증 정보 포함
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(auth_header);

        // SecuritySchemes 등록
        Components components = new Components()
                .addSecuritySchemes(auth_header, new SecurityScheme()
                        .name(auth_header)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(securityRequirement)
                .components(components);
    }
}
