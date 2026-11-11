package com.scrumdapp.realtimeservice.checkpoints.events

data class CheckpointSessionCreatedV1(
    val groupId: Long,
    val sessionId: Long,
    val sessionName: String
)