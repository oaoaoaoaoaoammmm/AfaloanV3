package org.example.afauser.controllers.auth.dtos

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema
data class RegisterUserResponse(
    @Schema(description = "Id нового пользователя")
    val id: UUID
)
