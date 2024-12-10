package org.example.afanotification.services

import org.example.afanotification.configurations.email.MailSenderProperties
import org.example.afanotification.models.EmailNotification
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.mockito.kotlin.mock
import org.springframework.mail.javamail.JavaMailSender
import java.time.LocalDateTime
import java.util.*

class EmailSenderTest {

    private val mailProps = MailSenderProperties(
        host = "host",
        port = 8888,
        username = "username",
        password = "password",
        protocol = "protocol",
        debug = "true"
    )
    private val mailSender = mock<JavaMailSender>()

    private val emailSender = EmailSender(mailSender, mailProps)

    @Test
    fun `sendEmail should execute successfully`() {

        assertDoesNotThrow { emailSender.sendEmail(createEmail()) }
    }

    private fun createEmail() = EmailNotification(
        id = UUID.randomUUID(),
        email = "test@example.com",
        title = "Test title",
        text = "Test text",
        localDateTime = LocalDateTime.now()
    )
}