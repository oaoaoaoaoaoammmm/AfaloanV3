package org.example.afauser.controllers.auth.dtos

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

@Schema(description = "Авторизация пользователя")
data class AuthorizeUserRequest(
    @Schema(description = "Логин")
    @field:Size(min = 9, max = 64)
    val username: String,
    @Schema(description = "Пароль")
    @field:Size(min = 5, max = 60)
    val password: String
)