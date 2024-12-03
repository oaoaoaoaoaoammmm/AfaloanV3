package org.example.afaprocess.kafka.messages

import java.time.LocalDateTime
import java.util.*

data class EmailNotification(
    val id: UUID = UUID.randomUUID(),
    val email: String,
    val title: String,
    val text: String,
    val localDateTime: LocalDateTime = LocalDateTime.now(),
)
