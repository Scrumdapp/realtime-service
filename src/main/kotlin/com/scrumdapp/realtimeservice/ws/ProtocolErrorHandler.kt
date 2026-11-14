package com.scrumdapp.realtimeservice.ws

import org.slf4j.LoggerFactory
import org.springframework.messaging.Message
import org.springframework.messaging.MessageDeliveryException
import org.springframework.web.socket.messaging.StompSubProtocolErrorHandler

class ProtocolErrorHandler : StompSubProtocolErrorHandler() {

    val logger = LoggerFactory.getLogger(javaClass)

    override fun handleClientMessageProcessingError(clientMessage: Message<ByteArray>?, ex: Throwable): Message<ByteArray>? {
        logger.error("Error happened", ex)
        if (ex is MessageDeliveryException) {
            return super.handleClientMessageProcessingError(clientMessage, ex.cause ?: ex)
        } else {
            return super.handleClientMessageProcessingError(clientMessage, ex)
        }
    }
}