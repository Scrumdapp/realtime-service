package com.scrumdapp.realtimeservice.security.authorizers.valueMatchers

import com.scrumdapp.passportplugin.jwt.PassportContent
import com.scrumdapp.realtimeservice.security.authorizers.ObjectAuthorizationContext

interface PassportAuthorizer {

    fun match(passport: PassportContent, obj: ObjectAuthorizationContext): Boolean

}