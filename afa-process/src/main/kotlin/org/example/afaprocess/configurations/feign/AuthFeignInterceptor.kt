package org.example.afaprocess.configurations.feign

import feign.RequestInterceptor
import feign.RequestTemplate
import org.example.afaprocess.utils.toJson
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class AuthFeignInterceptor: RequestInterceptor {

    override fun apply(request: RequestTemplate) {
        request.header(USER_ID, UUID.randomUUID().toString())
        request.header(USERNAME, "afa-process")
        request.header(USER_ROLES, listOf("SUPERVISOR", "WORKER", "CUSTOMER").toJson())
    }

    private companion object {
        const val USER_ID = "UserId"
        const val USERNAME = "Username"
        const val USER_ROLES = "UserRoles"
    }
}