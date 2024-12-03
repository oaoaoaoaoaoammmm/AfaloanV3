package org.example.afanotification.configurations.email

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailSenderConfiguration(
    private val mailSenderProperties: MailSenderProperties
) {

    @Bean
    fun javaMailSender(): JavaMailSender {
        val mailSender = JavaMailSenderImpl()
        mailSender.host = mailSenderProperties.host
        mailSender.port = mailSenderProperties.port
        mailSender.username = mailSenderProperties.username
        mailSender.password = mailSenderProperties.password
        mailSender.javaMailProperties.putAll(
            mapOf(
                Pair("mail.transport.protocol", mailSenderProperties.protocol),
                Pair("mail.debug", mailSenderProperties.debug),
            )
        )
        return mailSender
    }
}