package com.scrumdapp.realtimeservice.groupUsers

import com.scrumdapp.realtimeservice.groupUsers.dto.GroupUserAdded
import com.scrumdapp.realtimeservice.groupUsers.dto.GroupUserRemoved
import com.scrumdapp.realtimeservice.groupUsers.dto.GroupUserUpdated
import com.scrumdapp.realtimeservice.groupUsers.events.GroupUserAddedV1
import com.scrumdapp.realtimeservice.groupUsers.events.GroupUserRemovedV1
import com.scrumdapp.realtimeservice.groupUsers.events.GroupUserUpdatedV1
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Controller
import tools.jackson.databind.ObjectMapper

@Controller
class GroupUserKafkaListener(
    val template: SimpMessagingTemplate
) {
    val parser = ObjectMapper()

    @KafkaListener(topics = ["com.scrumdapp.groups.users.added.v1"])
    fun userAddedToGroup(@Payload data: GroupUserAddedV1) {
        template.convertAndSend("/groups/${data.groupId}/users.added", GroupUserAdded(data.userId))
    }

    @KafkaListener(topics = ["com.scrumdapp.groups.users.updated.v1"])
    fun groupUserUpdated(@Payload data: GroupUserUpdatedV1) {
        template.convertAndSend("/groups/${data.groupId}/users.updated", GroupUserUpdated(data.userId))
    }

    @KafkaListener(topics = ["com.scrumdapp.groups.users.removed.v1"])
    fun userRemovedFromGroup(@Payload data: GroupUserRemovedV1) {
        template.convertAndSend("/groups/${data.groupId}/users.removed", GroupUserRemoved(data.userId))
    }

}