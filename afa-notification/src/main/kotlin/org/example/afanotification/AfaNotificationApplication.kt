package org.example.afanotification

import org.example.afanotification.configurations.email.MailSenderProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(MailSenderProperties::class)
class AfaNotificationApplication

fun main(args: Array<String>) {
    runApplication<AfaNotificationApplication>(*args)
}
