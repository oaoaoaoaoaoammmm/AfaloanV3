package com.example.afaloan.controllers.microloans.dtos

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Size
import java.math.BigDecimal

@Schema(description = "Микрозайм")
data class MicroloanDto(
    @Schema(description = "Название")
    @field:Size(min = 1, max = 32)
    val name: String,
    @Schema(description = "Сумма")
    @field:Min(value = 1)
    val sum: BigDecimal,
    @Schema(description = "Проценты")
    @field:Min(value = 0)
    val monthlyInterest: BigDecimal,
    @Schema(description = "Условия")
    @field:Size(min = 1, max = 200)
    val conditions: String,
    @Schema(description = "Нужный доход")
    @field:Min(value = 1)
    val monthlyIncomeRequirement: BigDecimal,
    @Schema(description = "Другие условия")
    @field:Size(max = 200)
    val otherRequirements: String? = null
)
