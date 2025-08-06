package com.projet2_oc_y.projet2_oc_y.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        final String securityName = "bearerAuth";

        return new OpenAPI()
            .info(new Info().title("API Projet 2 OC").version("1.0").description("Documentation avec accès sécurisé à l'API"))
            .addSecurityItem(new SecurityRequirement().addList(securityName))
            .components(new Components()
                .addSecuritySchemes(securityName,
                    new SecurityScheme()
                        .name(securityName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")));
    }
}
