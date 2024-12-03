package com.example.afaloan.controllers.profiles.dtos

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.math.BigDecimal

@Schema
data class CreateProfileRequest(
    @Schema(description = "Имя")
    @field:Size(min = 1, max = 20)
    val name: String,
    @Schema(description = "Электронная почта")
    @field:Size(min = 9, max = 64)
    val username: String,
    @Schema(description = "Фамилия")
    @field:Size(min = 1, max = 20)
    val surname: String,
    @Schema(description = "Отчество")
    @field:Size(max = 20)
    val patronymic: String? = null,
    @Schema(description = "Номер телефона")
    @Pattern(regexp = "^\\+7\\d{10}$")
    val phoneNumber: String,
    @Schema(description = "Серия паспорта")
    @field:Size(min = 4, max = 4)
    val passportSeries: String,
    @Schema(description = "Номер паспорта")
    @field:Size(min = 6, max = 6)
    val passportNumber: String,
    @Schema(description = "СНИЛС")
    @field:Size(min = 14, max = 14)
    val snils: String? = null,
    @Schema(description = "ИНН")
    @field:Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}-\\d{2}$")
    val inn: String? = null,
    @Schema(description = "Месячный доход")
    @field:Min(1)
    val monthlyIncome: BigDecimal
)
