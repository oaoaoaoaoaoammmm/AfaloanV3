package org.example.afauser.controllers.auth.dtos

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(description = "Пользовательские данные")
data class AuthorizeUserResponse(
    @Schema(description = "Id пользователя")
    val id: UUID,
    @Schema(description = "Логин пользователя")
    val username: String,
    @Schema(description = "Access токен")
    val access: String,
    @Schema(description = "Refresh токен")
    val refresh: String
)