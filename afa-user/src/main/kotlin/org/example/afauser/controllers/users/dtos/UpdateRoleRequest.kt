package org.example.afauser.controllers.users.dtos

import io.swagger.v3.oas.annotations.media.Schema
import org.example.afauser.models.enumerations.Role

@Schema
data class UpdateRoleRequest(
    @Schema(description = "Роль")
    val role: Role
)
