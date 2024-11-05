package org.example.afaprocess.controllers.processes.dtos

import org.example.afaprocess.models.enumerations.ProcessStatus
import java.math.BigDecimal
import java.util.*

data class ProcessDto(
    val debt: BigDecimal,
    val comment: String,
    val status: ProcessStatus,
    val bidId: UUID
)
