package com.example.afaloan.controllers.microloans.dtos

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema
data class CreateMicroloanResponse(
    @Schema(description = "Id")
    val id: UUID
)
