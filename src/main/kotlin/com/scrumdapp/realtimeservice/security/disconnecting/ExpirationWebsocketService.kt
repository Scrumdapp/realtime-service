package com.scrumdapp.realtimeservice.security.disconnecting

import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.scheduling.TaskScheduler
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.stereotype.Service
import org.springframework.web.socket.WebSocketSession
import java.lang.Exception
import java.util.WeakHashMap
import java.util.concurrent.ScheduledFuture

@Service
class ExpirationWebsocketService(
    val scheduler: TaskScheduler,
) {

    val map = WeakHashMap<WebSocketSession, ScheduledFuture<*>>()

    fun registerSession(session: WebSocketSession) {
        if (map.containsKey(session)) return

        val principal = session.principal as? JwtAuthenticationToken ?: throw Exception("Couldn't authorize the user, the principal was not correct")
        val jwt = principal.token
        val expirationDate = jwt.expiresAt ?: throw Exception("No expirationDate present")
        val future = scheduler.schedule(DisconnectWebsocketRunner(session), expirationDate)
        map[session] = future
    }

    fun unregisterSession(session: WebSocketSession) {
        map[session]?.cancel(false)
        map[session] = null
    }
}