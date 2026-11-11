package com.scrumdapp.realtimeservice.security.authorizers

import com.scrumdapp.realtimeservice.security.authorizers.valueMatchers.ValueMatcher
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.authorization.AuthorizationResult
import org.springframework.security.core.Authentication
import org.springframework.security.messaging.access.intercept.MessageAuthorizationContext
import org.springframework.security.oauth2.jwt.Jwt
import java.util.function.Supplier

class PassportAuthorizer(
    val matcher: ValueMatcher
) : AuthorizationManager<MessageAuthorizationContext<*>> {

    override fun authorize(
        authentication: Supplier<out Authentication?>,
        obj: MessageAuthorizationContext<*>
    ): AuthorizationResult? {
        val passport = (authentication.get().principal as Jwt)
        TODO("Not yet implemented")
    }
}