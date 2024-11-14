package org.example.afauser.controllers.users.dtos

import io.swagger.v3.oas.annotations.media.Schema
import org.example.afauser.models.enumerations.Role
import java.util.*

@Schema
data class UserDto(
    @Schema(description = "Id пользователя")
    val id: UUID,
    @Schema(description = "Логин")
    val username: String,
    @Schema(description = "Подтвержден")
    val confirmed: Boolean,
    @Schema(description = "Подтверждена почта")
    val confirmedUsername: Boolean,
    @Schema(description = "Заблокирован")
    val blocked: Boolean,
    @Schema(description = "Роль")
    val role: Role
)
