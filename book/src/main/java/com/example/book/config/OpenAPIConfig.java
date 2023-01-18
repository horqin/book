package com.example.book.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "book", version = "0.0.1-SNAPSHOT", description = "book"))
public class OpenAPIConfig {
}
