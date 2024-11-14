package com.example.afaloan.models

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Schema(description = "Микрозайм")
@Entity
@Table(name = "microloans")
data class Microloan(

    @Schema(description = "Id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Schema(description = "Название")
    @Column(name = "name")
    val name: String,

    @Schema(description = "Сумма")
    @Column(name = "sum")
    val sum: BigDecimal,

    @Schema(description = "Проценты")
    @Column(name = "monthly_interest")
    val monthlyInterest: BigDecimal,

    @Schema(description = "Условия")
    @Column(name = "conditions")
    val conditions: String,

    @Schema(description = "Нужный доход")
    @Column(name = "monthly_income_requirement")
    val monthlyIncomeRequirement: BigDecimal,

    @Schema(description = "Другие условия")
    @Column(name = "other_requirements")
    val otherRequirements: String? = null
)