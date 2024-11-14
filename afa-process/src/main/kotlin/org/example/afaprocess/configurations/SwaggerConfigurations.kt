package org.example.afaprocess.configurations

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfigurations {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(Info().title("AfaProcess API").version("1.0"))
    }
}