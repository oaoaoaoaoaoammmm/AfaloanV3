package org.example.afanotification.services

import org.example.afanotification.configurations.email.MailSenderProperties
import org.example.afanotification.models.EmailNotification
import org.example.afanotification.utils.logger
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailSender(
    private val mailSender: JavaMailSender,
    private val mailProps: MailSenderProperties
) {

    fun sendEmail(email: EmailNotification) {
        val simpleMessage = SimpleMailMessage()
        simpleMessage.from = mailProps.username
        simpleMessage.setTo(email.email)
        simpleMessage.subject = email.title
        simpleMessage.text = email.text
        logger.info { "Sending message with id - ${email.id}, to ${email.email}" }
        mailSender.send(simpleMessage)
    }
}