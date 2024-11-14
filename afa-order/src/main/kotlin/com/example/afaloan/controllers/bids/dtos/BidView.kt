package com.example.afaloan.controllers.bids.dtos

import com.example.afaloan.models.enumerations.BidPriority
import com.example.afaloan.models.enumerations.BidStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime
import java.util.*

@Schema(description = "Отображение заявок")
data class BidView(
    @Schema(description = "Id")
    val id: UUID? = null,
    @Schema(description = "Цель")
    val target: String,
    @Schema(description = "Дата создания заявки")
    val date: LocalDateTime,
    @Schema(description = "Приоритет")
    val priority: BidPriority,
    @Schema(description = "Статус")
    val status: BidStatus,
)
