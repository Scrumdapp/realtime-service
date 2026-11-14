package com.scrumdapp.realtimeservice.groupUsers.events

data class UserAddedToGroupV1(
    val userId: Long,
    val groupId: Long
)
