package com.scrumdapp.realtimeservice.ws

import com.scrumdapp.realtimeservice.security.disconnecting.ExpirationWebsocketDecorator
import com.scrumdapp.realtimeservice.security.disconnecting.ExpirationWebsocketService
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.TaskScheduler
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.WebSocketTransportRegistration


@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfiguration(
    val expirationService: ExpirationWebsocketService
) : WebSocketMessageBrokerConfigurer {

    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry.apply {
            addEndpoint("/ws")
            setErrorHandler(ProtocolErrorHandler())
        }
    }

    override fun configureWebSocketTransport(registry: WebSocketTransportRegistration) {
        registry.addDecoratorFactory { h -> ExpirationWebsocketDecorator(h, expirationService) }
    }
}