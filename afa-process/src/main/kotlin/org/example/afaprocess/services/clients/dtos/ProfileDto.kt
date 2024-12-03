package org.example.afaprocess.services.clients.dtos

import java.math.BigDecimal
import java.util.*

data class ProfileDto(
    val id: UUID? = null,
    val username: String,
    val name: String,
    val surname: String,
    val patronymic: String? = null,
    val phoneNumber: String,
    val passportSeries: String,
    val passportNumber: String,
    val snils: String? = null,
    val inn: String? = null,
    val monthlyIncome: BigDecimal
)
