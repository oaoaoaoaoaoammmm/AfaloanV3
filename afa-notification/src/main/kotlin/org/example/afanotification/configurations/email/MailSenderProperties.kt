package org.example.afanotification.configurations.email

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spring.mail")
data class MailSenderProperties@ConstructorBinding constructor(
    val host: String,
    val port: Int,
    val username: String,
    val password: String,
    val protocol: String,
    val debug: String
)