package com.scrumdapp.realtimeservice.groupUsers

import com.scrumdapp.realtimeservice.groupUsers.dto.UserAdded
import com.scrumdapp.realtimeservice.groupUsers.dto.UserRemoved
import com.scrumdapp.realtimeservice.groupUsers.events.UserAddedToGroupV1
import com.scrumdapp.realtimeservice.groupUsers.events.UserRemovedFromGroupV1
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import tools.jackson.databind.ObjectMapper

@Controller
class GroupUserKafkaListener(
    val template: SimpMessagingTemplate
) {
    val parser = ObjectMapper()

    @KafkaListener(topics = ["com.scrumdapp.groups.users.added.v1"])
    fun userAddedToGroup(event: String) {
        val data = parser.readValue(event, UserAddedToGroupV1::class.java)
        template.convertAndSend("/groups/${data.groupId}/users.added", UserAdded(data.userId))
    }

    @KafkaListener(topics = ["com.scrumdapp.groups.users.removed.v1"])
    fun userRemovedFromGroup(event: String) {
        val data = parser.readValue(event, UserRemovedFromGroupV1::class.java)
        template.convertAndSend("/groups/${data.groupId}/users.removed", UserRemoved(data.userId))
    }

}