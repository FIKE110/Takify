package com.chat.fortunechatbackendwithspring.config.swagger

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class SwaggerConfig {

    @Bean
    public fun swagger(): OpenAPI {
        return OpenAPI()
            .info(Info()
                .title("Api for fortune backend")
                .version("v1")
                .description("this is my backend api for chats")
            )
    }
}