package org.example.afaprocess.services

import org.example.afaprocess.kafka.messages.EmailNotification
import org.example.afaprocess.models.Process
import org.example.afaprocess.services.clients.dtos.ProfileDto
import org.springframework.stereotype.Service

@Service
class TemplateService {

    fun convertToEmailMessage(pair: Pair<ProfileDto, Process>): EmailNotification {
        val profile = pair.first
        val process = pair.second
        return EmailNotification(
            email = profile.username,
            title = "Напоминание о нашем общем благе благе",
            text = """Добрый день, ${profile.name}!
                |Начислены проценты по вашему микрозайму. Текущий долг - ${process.debt}""".trimMargin()
        )
    }
}