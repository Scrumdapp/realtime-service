package com.scrumdapp.realtimeservice.ws

import org.springframework.messaging.Message
import org.springframework.messaging.MessageHandlingException
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.messaging.simp.stomp.StompCommand
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.messaging.support.MessageBuilder
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
class MessageExceptionHandler(
    val template: SimpMessagingTemplate
) {
    @MessageExceptionHandler(MessageHandlingException::class)
    private fun handleException(handler: MessageHandlingException, clientMessage: Message<*>) {
        val accessor = StompHeaderAccessor.create(StompCommand.MESSAGE)
        val sessionId = SimpMessageHeaderAccessor.getFirstNativeHeader("receipt", clientMessage.headers)

        accessor.addNativeHeader("receipt-id", sessionId)
        accessor.destination = "/user/queue/errors"

        val err = handler.cause ?: handler
        val message = MessageBuilder.createMessage((err.message ?: "An error occurred").toByteArray(), accessor.messageHeaders)
        template.send(message)
    }

}