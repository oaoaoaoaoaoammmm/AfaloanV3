package org.example.afafile.configurations.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.afafile.utils.toObject
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class AuthenticationFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val userId = request.getHeader(USER_ID)
        val username = request.getHeader(USERNAME)?.takeIf { it.isNotBlank() }
        val userRoles = request.getHeader(USER_ROLES)?.toObject<List<String>>()?.map { SimpleGrantedAuthority(it) }
        if (userId != null && username != null && userRoles != null) {
            SecurityContextHolder.getContext().authentication =
                UsernamePasswordAuthenticationToken(userId, username, userRoles)
        }
        filterChain.doFilter(request, response)
    }

    private companion object {
        const val USER_ID = "UserId"
        const val USERNAME = "Username"
        const val USER_ROLES = "UserRoles"
    }
}