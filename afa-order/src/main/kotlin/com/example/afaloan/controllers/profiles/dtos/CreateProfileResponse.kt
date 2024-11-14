package com.example.afaloan.controllers.profiles.dtos

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema
data class CreateProfileResponse(
    @Schema(description = "Id")
    val id: UUID
)
