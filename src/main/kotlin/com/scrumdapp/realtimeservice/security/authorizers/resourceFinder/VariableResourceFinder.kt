package com.scrumdapp.realtimeservice.security.authorizers.resourceFinder

import com.scrumdapp.passportplugin.jwt.PassportContent
import com.scrumdapp.realtimeservice.security.authorizers.ObjectAuthorizationContext

class VariableResourceFinder(
    val variableName: String
) : ResourceFinder {
    override fun findResource(passport: PassportContent, obj: ObjectAuthorizationContext): String {
        return obj.variables.getOrElse(variableName) {
            throw NullPointerException("The key '$variableName' was not found!")
        }
    }
}