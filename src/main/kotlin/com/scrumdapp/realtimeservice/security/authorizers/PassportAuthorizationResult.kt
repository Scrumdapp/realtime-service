package com.scrumdapp.realtimeservice.security.authorizers

import org.springframework.security.authorization.AuthorizationResult

data class PassportAuthorizationResult(val granted: Boolean) : AuthorizationResult {
    override fun isGranted(): Boolean {
        return granted
    }
}