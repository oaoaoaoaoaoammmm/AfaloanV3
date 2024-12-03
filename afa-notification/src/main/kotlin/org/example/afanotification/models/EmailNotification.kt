package org.example.afanotification.models

import java.time.LocalDateTime
import java.util.UUID

data class EmailNotification(
    val id: UUID,
    val email: String,
    val title: String,
    val text: String,
    val localDateTime: LocalDateTime
)