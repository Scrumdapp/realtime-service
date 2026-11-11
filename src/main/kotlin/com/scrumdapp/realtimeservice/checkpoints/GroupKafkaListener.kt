package com.scrumdapp.realtimeservice.checkpoints

import com.scrumdapp.realtimeservice.checkpoints.dto.CheckpointSessionCreatedEvent
import com.scrumdapp.realtimeservice.checkpoints.dto.CheckpointUpdatedEvent
import com.scrumdapp.realtimeservice.checkpoints.events.CheckpointSessionCreatedV1
import com.scrumdapp.realtimeservice.checkpoints.events.UserCheckpointUpdatedV1
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import tools.jackson.databind.ObjectMapper

@Controller
class GroupKafkaListener(
    val template: SimpMessagingTemplate
) {
    val parser = ObjectMapper()

    @KafkaListener(topics = ["com.scrumdapp.checkpoints.session.created.v1"])
    fun sessionCreated(event: String) {
        val data = parser.readValue(event, CheckpointSessionCreatedV1::class.java)
        template.convertAndSend("/groups/${data.groupId}/checkpoints.created", CheckpointSessionCreatedEvent(data.sessionId))
    }

    @KafkaListener(topics = ["com.scrumdapp.checkpoints.updated.v1"])
    fun checkpointUpdated(event: String) {
        val data = parser.readValue(event, UserCheckpointUpdatedV1::class.java)
        template.convertAndSend("/sessions/${data.sessionId}/checkpoints.updated", CheckpointUpdatedEvent(data.userId))
    }

}