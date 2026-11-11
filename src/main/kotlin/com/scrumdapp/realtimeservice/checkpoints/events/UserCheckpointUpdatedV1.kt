package com.scrumdapp.realtimeservice.checkpoints.events

data class UserCheckpointUpdatedV1(
    val userId: Long,
    val groupId: Long,
    val sessionId: Long,
)