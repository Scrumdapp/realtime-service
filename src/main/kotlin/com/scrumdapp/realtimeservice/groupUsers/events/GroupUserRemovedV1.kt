package com.scrumdapp.realtimeservice.groupUsers.events

data class GroupUserRemovedV1(
    val userId: Long,
    val groupId: Long
)
