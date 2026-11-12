package com.scrumdapp.realtimeservice.groupUsers.events

data class UserRemovedFromGroupV1(
    val userId: Long,
    val groupId: Long
)
