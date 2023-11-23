package com.api.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("spApi v1")
            .pathsToMatch("/api/**")
            .build();
    }
    @Bean
    public OpenAPI spOpenAPI() {
        return new OpenAPI()
            .info(new Info().title("spApi API")
                .description("spApi API 명세서입니다.")
                .version("v0.0.1"));
    }
}
