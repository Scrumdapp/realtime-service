package com.scrumdapp.realtimeservice.security

import com.scrumdapp.passportplugin.filters.PassportAuthFilter
import com.scrumdapp.passportplugin.filters.usePassport
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain

@Configuration
class WebsocketSecurity {

    @Bean
    fun securityWebFilterChain(
        http: HttpSecurity,
        passportAuthFilter: PassportAuthFilter,
    ): DefaultSecurityFilterChain = http.apply {
        csrf { it.disable() }
        usePassport(passportAuthFilter)
        authorizeHttpRequests { it.apply {
            requestMatchers("/ws").authenticated()
            anyRequest().denyAll()
        }}
    }.build()


}