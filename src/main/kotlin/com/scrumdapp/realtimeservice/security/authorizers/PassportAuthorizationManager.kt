package com.scrumdapp.realtimeservice.security.authorizers

import com.scrumdapp.passportplugin.jwt.PassportService
import com.scrumdapp.realtimeservice.security.authorizers.valueMatchers.PassportAuthorizer
import org.springframework.security.authorization.AuthorizationManager
import org.springframework.security.authorization.AuthorizationResult
import org.springframework.security.core.Authentication
import org.springframework.security.messaging.access.intercept.MessageAuthorizationContext
import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager
import org.springframework.security.oauth2.jwt.Jwt
import java.util.function.Supplier

class PassportAuthorizationManager(
    val service: PassportService,
    val matcher: PassportAuthorizer
) : AuthorizationManager<MessageAuthorizationContext<*>> {

    override fun authorize(
        authentication: Supplier<out Authentication?>,
        obj: MessageAuthorizationContext<*>
    ): AuthorizationResult {
        val passport = service.extractPassport(authentication.get().principal as Jwt)
        val result = matcher.match(passport, ObjectAuthorizationContext(obj.variables))
        return PassportAuthorizationResult(result)
    }
}

data class PassportAuthorizationResult(val granted: Boolean) : AuthorizationResult {
    override fun isGranted(): Boolean {
        return granted
    }
}

class ObjectAuthorizationContext(
    val variables: Map<String, String>
)

fun MessageMatcherDelegatingAuthorizationManager.Builder.Constraint.passport(service: PassportService, matcher: PassportAuthorizer) {

}
