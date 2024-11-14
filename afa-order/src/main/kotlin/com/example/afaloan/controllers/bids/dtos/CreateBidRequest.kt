package com.example.afaloan.controllers.bids.dtos

import com.example.afaloan.models.enumerations.BidPriority
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import java.util.*

@Schema(description = "Создание заявки")
data class CreateBidRequest(
    @Schema(description = "Цель")
    @field:Size(min = 1, max = 64)
    val target: String,
    @Schema(description = "Сопровождающее письмо")
    @field:Size(max = 500)
    val coverLetter: String? = null,
    @Schema(description = "Приоритет")
    val priority: BidPriority,
    @Schema(description = "Сообщение от сотрудника")
    @field:Size(max = 64)
    val employeeMessage: String? = null,
    @Schema(description = "Id профиля")
    val profileId: UUID,
    @Schema(description = "Id микрозайма")
    val microloanId: UUID,
    @Schema(description = "Id точки кипения")
    val boilingPointId: UUID,
)
