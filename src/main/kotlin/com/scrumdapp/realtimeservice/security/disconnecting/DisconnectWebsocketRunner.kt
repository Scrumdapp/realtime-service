package com.scrumdapp.realtimeservice.security.disconnecting

import org.springframework.web.socket.WebSocketSession

class DisconnectWebsocketRunner(
    val session: WebSocketSession
) : Runnable {

    override fun run() {
        session.close()
    }

}