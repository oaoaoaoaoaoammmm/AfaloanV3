package com.example.afaloan.controllers.profiles.dtos

import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.util.*

@Schema(description = "Профиль")
data class ProfileDto(
    @Schema(description = "Id")
    val id: UUID? = null,
    @Schema(description = "Электронная почта")
    val username: String,
    @Schema(description = "Имя")
    val name: String,
    @Schema(description = "Фамилия")
    val surname: String,
    @Schema(description = "Отчество")
    val patronymic: String? = null,
    @Schema(description = "Номер телефона")
    val phoneNumber: String,
    @Schema(description = "Серия паспорта")
    val passportSeries: String,
    @Schema(description = "Номер паспорта")
    val passportNumber: String,
    @Schema(description = "СНИЛС")
    val snils: String? = null,
    @Schema(description = "ИНН")
    val inn: String? = null,
    @Schema(description = "Месячный доход")
    val monthlyIncome: BigDecimal
)
