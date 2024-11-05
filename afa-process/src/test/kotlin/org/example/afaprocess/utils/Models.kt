package org.example.afaprocess.utils

import org.example.afaprocess.controllers.processes.dtos.CreateProcessRequest
import org.example.afaprocess.controllers.processes.dtos.ProcessDto
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.example.afaprocess.models.Process
import org.example.afaprocess.models.enumerations.ProcessStatus
import java.math.BigDecimal
import java.util.UUID

const val USERNAME = "username"
val USER_ID: UUID = UUID.fromString("abf3785d-089d-42b2-a6cd-53cdb3d86752")
val USER_ROLES = listOf(SimpleGrantedAuthority("SUPERVISOR"))


fun createProcess() = Process(
    id = UUID.randomUUID(),
    debt = BigDecimal.valueOf(10.00),
    comment = "comment",
    status = ProcessStatus.IN_PROCESSING,
    bidId = UUID.randomUUID()
)

fun createCreateProcessRequest() = CreateProcessRequest(
    debt = BigDecimal.valueOf(10.00),
    comment = "comment",
    bidId = UUID.randomUUID()
)

fun createProcessDto() = ProcessDto(
    debt = BigDecimal.valueOf(10.00),
    comment = "comment",
    status = ProcessStatus.CLOSED,
    bidId = UUID.randomUUID()
)