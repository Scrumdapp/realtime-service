package com.scrumdapp.realtimeservice.security.disconnecting

import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.WebSocketHandlerDecorator

class ExpirationWebsocketDecorator(
    handler: WebSocketHandler,
    val expirationService: ExpirationWebsocketService
) : WebSocketHandlerDecorator(handler) {

    override fun afterConnectionEstablished(session: WebSocketSession) {
        super.afterConnectionEstablished(session)
        expirationService.registerSession(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, closeStatus: CloseStatus) {
        super.afterConnectionClosed(session, closeStatus)
        expirationService.unregisterSession(session)
    }

}