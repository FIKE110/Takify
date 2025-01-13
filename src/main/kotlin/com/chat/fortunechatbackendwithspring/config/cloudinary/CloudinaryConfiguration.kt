package com.chat.fortunechatbackendwithspring.config.cloudinary

import com.cloudinary.Cloudinary
import com.cloudinary.utils.ObjectUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class CloudinaryConfiguration {

    @Value("\${cloudinary.cloud-name}")
    private lateinit var cloud_name:String
    @Value("\${cloudinary.api-key}")
    private lateinit var api_key:String
    @Value("\${cloudinary.api-secret}")
    private lateinit var api_secret:String

        @Bean
        fun cloudinary(): Cloudinary {
            val cloudinary = Cloudinary(
                ObjectUtils.asMap(
                    "cloud_name", cloud_name,
                    "api_key", api_key,
                    "api_secret", api_secret,
                    "secure", true
                )
            )

            return cloudinary
        }
}