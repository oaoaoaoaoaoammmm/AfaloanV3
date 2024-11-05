package org.example.afaprocess.controllers.processes.dtos

import org.example.afaprocess.models.enumerations.ProcessStatus
import java.math.BigDecimal
import java.util.*

data class ProcessView(
    val id: UUID,
    val debt: BigDecimal,
    val status: ProcessStatus,
    val comment: String
)
