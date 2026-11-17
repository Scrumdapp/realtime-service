package com.scrumdapp.realtimeservice.groupUsers

import com.scrumdapp.realtimeservice.groupUsers.dto.GroupUserAdded
import com.scrumdapp.realtimeservice.groupUsers.dto.GroupUserRemoved
import com.scrumdapp.realtimeservice.groupUsers.dto.GroupUserUpdated
import com.scrumdapp.realtimeservice.groupUsers.events.GroupUserAddedV1
import com.scrumdapp.realtimeservice.groupUsers.events.GroupUserRemovedV1
import com.scrumdapp.realtimeservice.groupUsers.events.GroupUserUpdatedV1
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
        val data = parser.readValue(event, GroupUserAddedV1::class.java)
        template.convertAndSend("/groups/${data.groupId}/users.added", GroupUserAdded(data.userId))
    }

    @KafkaListener(topics = ["com.scrumdapp.groups.users.removed.v1"])
    fun userRemovedFromGroup(event: String) {
        val data = parser.readValue(event, GroupUserRemovedV1::class.java)
        template.convertAndSend("/groups/${data.groupId}/users.removed", GroupUserRemoved(data.userId))
    }

    @KafkaListener(topics = ["com.scrumdapp.groups.users.updated.v1"])
    fun groupUserUpdated(event: String) {
        val data = parser.readValue(event, GroupUserUpdatedV1::class.java)
        template.convertAndSend("/groups/${data.groupId}/users.added", GroupUserUpdated(data.userId))
    }

}