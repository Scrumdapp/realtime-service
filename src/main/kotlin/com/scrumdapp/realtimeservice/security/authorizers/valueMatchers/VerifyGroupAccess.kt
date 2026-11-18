package com.scrumdapp.realtimeservice.security.authorizers.valueMatchers

import com.scrumdapp.passportplugin.jwt.PassportContent
import com.scrumdapp.realtimeservice.security.authorizers.ObjectAuthorizationContext
import com.scrumdapp.realtimeservice.security.authorizers.resourceFinder.ResourceFinder

class VerifyGroupAccess(
    val resourceFinder: ResourceFinder
) : PassportAuthorizer {
    override fun match(passport: PassportContent, obj: ObjectAuthorizationContext): Boolean {
        val currentValueStr = resourceFinder.findResource(passport, obj)
        val currentValue = currentValueStr.toLongOrNull() ?: return false
        return passport.userGroups.any { it == currentValue }
    }
}