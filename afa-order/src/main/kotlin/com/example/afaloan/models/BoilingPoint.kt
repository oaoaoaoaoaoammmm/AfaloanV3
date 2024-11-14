package com.example.afaloan.models

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "boiling_points")
@Schema
data class BoilingPoint(

    @Schema(description = "Id")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Schema(description = "Название города")
    @Column(name = "city")
    val city: String,

    @Schema(description = "Адрес")
    @Column(name = "address")
    val address: String,

    @Schema(description = "Часы работы")
    @Column(name = "opening_hours")
    val openingHours: String,

    @Schema(description = "Информация")
    @Column(name = "info")
    val info: String? = null
)
