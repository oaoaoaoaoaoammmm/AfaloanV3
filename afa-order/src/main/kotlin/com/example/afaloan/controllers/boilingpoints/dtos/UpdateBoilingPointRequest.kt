package com.example.afaloan.controllers.boilingpoints.dtos

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

@Schema(description = "Обновление точки кипения")
data class UpdateBoilingPointRequest(
    @Schema(description = "Название города")
    @field:Size(min = 3, max = 32)
    val city: String,
    @Schema(description = "Адрес")
    @field:Size(min = 3, max = 64)
    val address: String,
    @Schema(description = "Время работы")
    @field:Size(min = 10, max = 100)
    val openingHours: String,
    @Schema(description = "Информация")
    @field:Size(max = 200)
    val info: String? = null
)