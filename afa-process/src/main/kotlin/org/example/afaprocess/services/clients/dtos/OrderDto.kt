package org.example.afaprocess.services.clients.dtos

import java.time.LocalDateTime
import java.util.*

data class OrderDto(
    val target: String,
    val coverLetter: String? = null,
    val date: LocalDateTime,
    val priority: String,
    val status: String,
    val employeeMessage: String? = null,
    val profileId: UUID,
    val microloanId: UUID,
    val boilingPointId: UUID,
)