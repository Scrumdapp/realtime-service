package com.scrumdapp.realtimeservice.security

import com.scrumdapp.passportplugin.jwt.PassportService
import com.scrumdapp.realtimeservice.security.authorizers.PassportAuthorizationManager
import com.scrumdapp.realtimeservice.security.authorizers.passport
import com.scrumdapp.realtimeservice.security.authorizers.resourceFinder.VariableResourceFinder
import com.scrumdapp.realtimeservice.security.authorizers.valueMatchers.VerifyGroupAccess
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.messaging.support.ChannelInterceptor
import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager

@Configuration
@EnableWebSocketSecurity
class WebsocketSecurityConfig {

    @Bean
    fun messageAuthorizationManager(
        passportService: PassportService,
        builder: MessageMatcherDelegatingAuthorizationManager.Builder
    ) = builder.apply {
        simpMessageDestMatchers("/app/**").authenticated()
        simpSubscribeDestMatchers("/topic/**").authenticated()
        simpSubscribeDestMatchers("/groups/{groupId}/users.added").passport(passportService, VerifyGroupAccess(VariableResourceFinder("groupId")))
        simpSubscribeDestMatchers("/user/queue/errors").authenticated()
        nullDestMatcher().authenticated()
        anyMessage().denyAll()
    }.build()


    @Bean
    fun csrfChannelInterceptor(): ChannelInterceptor {
        return object : ChannelInterceptor {}
    }
}