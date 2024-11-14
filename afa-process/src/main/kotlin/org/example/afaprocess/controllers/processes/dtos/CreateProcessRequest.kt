package org.example.afaprocess.controllers.processes.dtos

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import java.math.BigDecimal
import java.util.UUID

@Schema
data class CreateProcessRequest(
    @Schema(description = "Долг")
    @field:Min(1)
    val debt: BigDecimal,
    @Schema(description = "Комментарий")
    @field:Size(min = 1, max = 64)
    val comment: String,
    @Schema(description = "Id заявки")
    val bidId: UUID
)
