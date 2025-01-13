package com.chat.fortunechatbackendwithspring.config.security

import com.chat.fortunechatbackendwithspring.filter.IPFillter
import com.chat.fortunechatbackendwithspring.filter.JwtFilter
import com.chat.fortunechatbackendwithspring.model.User
import com.chat.fortunechatbackendwithspring.service.UserDaoService
import com.chat.fortunechatbackendwithspring.service.UserService
import jakarta.servlet.http.HttpSession
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.server.SecurityWebFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig(private val userService: UserService,private val jwtFilter: JwtFilter,authenticationConfiguration: AuthenticationConfiguration) {

    @Bean
    fun securityFilterChain(http: HttpSecurity, httpSession: HttpSession): SecurityFilterChain {
        http
            .csrf{
                csrf->csrf.disable()
            }
            .authorizeRequests{
                req->req
                    .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/actuator","/actuator/**").permitAll()
                .requestMatchers("/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html" ,
                    "/api/v1/users").permitAll()
                    .anyRequest()
                    .authenticated()
            }
            .sessionManagement {
                    httpSession->httpSession.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
         .addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter::class.java)



        return http.build()
    }


    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }


    @Bean
    fun authentionProvider(userDaoService: UserDaoService): AuthenticationProvider {
        val daoAuthenticationProvider=DaoAuthenticationProvider()
        daoAuthenticationProvider.setUserDetailsService(userDaoService)
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder())
        return daoAuthenticationProvider
    }

    @Bean
    fun authenticationManager(authenticationConfiguration: AuthenticationConfiguration): AuthenticationManager {
        return authenticationConfiguration.authenticationManager
    }

}