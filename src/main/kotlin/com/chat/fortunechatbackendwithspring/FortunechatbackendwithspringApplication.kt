package com.chat.fortunechatbackendwithspring

import com.chat.fortunechatbackendwithspring.model.User
import com.chat.fortunechatbackendwithspring.service.UserService
import org.apache.hc.core5.http.HttpEntity
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.http.HttpMethod
import org.springframework.util.function.SingletonSupplier
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.servlet.config.annotation.EnableWebMvc

@SpringBootApplication
class FortunechatbackendwithspringApplication{

	@Bean
	fun commandLineRunner(userService: UserService, restTemplate: RestTemplate, webClientBuilder: WebClient.Builder,
						  restClientBuilder: RestClient.Builder
	): CommandLineRunner {
		return CommandLineRunner {
				args ->
			run {
				println(
					"server is up and running"
				)
			}
		}
	}
}

fun main(args: Array<String>) {
	runApplication<FortunechatbackendwithspringApplication>(*args)
}


