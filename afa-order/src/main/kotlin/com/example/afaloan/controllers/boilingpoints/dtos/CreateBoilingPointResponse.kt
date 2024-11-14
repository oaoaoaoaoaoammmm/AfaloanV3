package com.example.afaloan.controllers.boilingpoints.dtos

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema
data class CreateBoilingPointResponse(
    @Schema(description = "Id")
    val id: UUID
)
