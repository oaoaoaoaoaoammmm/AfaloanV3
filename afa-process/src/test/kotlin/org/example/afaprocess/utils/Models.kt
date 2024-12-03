package org.example.afaprocess.utils

import org.example.afaprocess.controllers.processes.dtos.CreateProcessRequest
import org.example.afaprocess.controllers.processes.dtos.ProcessDto
import org.example.afaprocess.kafka.messages.EmailNotification
import org.example.afaprocess.models.Process
import org.example.afaprocess.models.enumerations.ProcessStatus
import org.example.afaprocess.services.clients.dtos.MicroloanDto
import org.example.afaprocess.services.clients.dtos.OrderDto
import org.example.afaprocess.services.clients.dtos.ProfileDto
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

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

fun createOrderDto() = OrderDto(
    target = "target",
    coverLetter = "cover letter",
    date = LocalDateTime.now(),
    priority = "priority",
    status = "status",
    employeeMessage = "employee message",
    profileId = UUID.randomUUID(),
    microloanId = UUID.randomUUID(),
    boilingPointId = UUID.randomUUID()
)

fun createMicroloanDto() = MicroloanDto(
    name = "name",
    sum = BigDecimal.valueOf(10.00),
    monthlyInterest = BigDecimal.valueOf(10.00),
    conditions = "conditions",
    monthlyIncomeRequirement = BigDecimal.valueOf(10.00),
    otherRequirements = "other requirements"
)

fun createProfileDto() = ProfileDto(
    id = UUID.randomUUID(),
    username = USERNAME,
    name = "name",
    surname = "surname",
    patronymic = "patronymic",
    phoneNumber = "89832422045",
    passportSeries = "1234",
    passportNumber = "123456",
    snils = "snils",
    inn = "inn",
    monthlyIncome = BigDecimal.valueOf(10.00)
)

fun createEmailNotification() = EmailNotification(
    email = "email@example.com",
    title = "title",
    text = "text"
)