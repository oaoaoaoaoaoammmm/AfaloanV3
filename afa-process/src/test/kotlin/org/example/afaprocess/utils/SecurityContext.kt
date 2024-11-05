package org.example.afaprocess.utils

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

fun createAuthentication() = UsernamePasswordAuthenticationToken(
    USER_ID.toString(),
    USERNAME,
    USER_ROLES
)