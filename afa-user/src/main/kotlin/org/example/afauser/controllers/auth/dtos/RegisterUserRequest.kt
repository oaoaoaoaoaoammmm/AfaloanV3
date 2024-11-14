package org.example.afauser.controllers.auth.dtos

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import org.example.afauser.models.enumerations.Role

@Schema(description = "Регистрация пользователя")
data class RegisterUserRequest(
    @Schema(description = "Логин/Почта")
    @field:Size(min = 9, max = 64)
    val username: String,
    @Schema(description = "Пароль")
    @field:Size(min = 5, max = 60)
    val password: String,
    @Schema(description = "Роль")
    val role: Role
)
