package com.example.afaloan.controllers.bids.dtos

import com.example.afaloan.models.enumerations.BidPriority
import com.example.afaloan.models.enumerations.BidStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.UUID

@Schema(description = "Заявка")
data class BidDto(
    @Schema(description = "Цель")
    val target: String,
    @Schema(description = "Сопровождающее письмо")
    val coverLetter: String? = null,
    @Schema(description = "Дата создания заявки")
    val date: LocalDateTime,
    @Schema(description = "Приоритет")
    val priority: BidPriority,
    @Schema(description = "Статус")
    val status: BidStatus,
    @Schema(description = "Сообщение от сотрудника")
    val employeeMessage: String? = null,
    @Schema(description = "Id профиля")
    val profileId: UUID,
    @Schema(description = "Id микрозайма")
    val microloanId: UUID,
    @Schema(description = "Id точки кипения")
    val boilingPointId: UUID,
)
