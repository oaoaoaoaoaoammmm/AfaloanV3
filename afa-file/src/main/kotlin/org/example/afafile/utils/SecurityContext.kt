package org.example.afafile.utils

import org.example.afafile.exceptions.ErrorCode
import org.example.afafile.exceptions.InternalException
import org.springframework.http.HttpStatus
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

object SecurityContext {

    fun getAuthorizedUserId() = getAuthentication()?.id
        ?: throw InternalException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN)

    fun getAuthorizedUserUsername() = getAuthentication()?.username
        ?: throw InternalException(HttpStatus.FORBIDDEN, ErrorCode.FORBIDDEN)

    private val Authentication.id: UUID
        get() = UUID.fromString(this.principal as String)

    private val Authentication.username: String
        get() = this.credentials as String

    private fun getAuthentication() = SecurityContextHolder.getContext().authentication
}