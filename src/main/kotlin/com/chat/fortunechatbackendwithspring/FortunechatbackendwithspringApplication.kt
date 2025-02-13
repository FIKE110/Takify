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
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.view.RedirectView

@SpringBootApplication
@RestController
class FortunechatbackendwithspringApplication{

	@GetMapping("/")
	fun rootHello():RedirectView{
		return RedirectView("/swagger-ui/index.html")
	}

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


class HelloResponse{
	private val message: String;
	private val urlToDocs:String;

	constructor(urlToDocs: String, message: String) {
		this.urlToDocs=urlToDocs;
		this.message=message;
	}
}


