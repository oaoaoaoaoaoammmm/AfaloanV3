package org.example.afaprocess.controllers.processes.dtos

import io.swagger.v3.oas.annotations.media.Schema
import org.example.afaprocess.models.enumerations.ProcessStatus
import java.math.BigDecimal
import java.util.*

@Schema
data class ProcessDto(
    @Schema(description = "Долг")
    val debt: BigDecimal,
    @Schema(description = "Комментарий")
    val comment: String,
    @Schema(description = "Статус")
    val status: ProcessStatus,
    @Schema(description = "Id заявки")
    val bidId: UUID
)
