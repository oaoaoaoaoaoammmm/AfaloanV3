package org.example.afaprocess.services.clients.dtos

import java.math.BigDecimal

data class MicroloanDto(
    val name: String,
    val sum: BigDecimal,
    val monthlyInterest: BigDecimal,
    val conditions: String,
    val monthlyIncomeRequirement: BigDecimal,
    val otherRequirements: String? = null
)