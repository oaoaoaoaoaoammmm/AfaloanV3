package org.example.afaprocess.configurations.security

import org.example.afaprocess.utils.toObject
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
class AuthenticationFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        val userId = exchange.request.headers[USER_ID]?.first()
        val username = exchange.request.headers[USERNAME]?.first()?.takeIf { it.isNotBlank() }
        val userRoles = exchange.request.headers[USER_ROLES]
            ?.first()?.toObject<List<String>>()?.map { SimpleGrantedAuthority(it) }
        if (userId != null && username != null && userRoles != null) {
            val auth = UsernamePasswordAuthenticationToken(userId, username, userRoles)
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
        } else {
            return chain.filter(exchange)
        }
    }

    private companion object {
        const val USER_ID = "UserId"
        const val USERNAME = "Username"
        const val USER_ROLES = "UserRoles"
    }
}