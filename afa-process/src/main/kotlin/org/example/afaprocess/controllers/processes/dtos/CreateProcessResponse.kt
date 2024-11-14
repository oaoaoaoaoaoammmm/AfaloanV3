package org.example.afaprocess.controllers.processes.dtos

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema
data class CreateProcessResponse(
    @Schema(description = "Id")
    val id: UUID
)
