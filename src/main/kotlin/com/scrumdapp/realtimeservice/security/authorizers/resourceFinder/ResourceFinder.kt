package com.scrumdapp.realtimeservice.security.authorizers.resourceFinder

import com.scrumdapp.passportplugin.jwt.PassportContent
import com.scrumdapp.realtimeservice.security.authorizers.ObjectAuthorizationContext

interface ResourceFinder {
    fun findResource(passport: PassportContent, obj: ObjectAuthorizationContext): String
}