package com.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@OpenAPIDefinition(servers = {@Server(url = "/", description = "localhost")})
public class SpApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpApiApplication.class, args);
    }

}
