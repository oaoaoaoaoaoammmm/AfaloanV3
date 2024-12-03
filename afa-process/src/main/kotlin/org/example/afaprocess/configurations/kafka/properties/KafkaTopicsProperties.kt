package org.example.afaprocess.configurations.kafka.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "spring.kafka.topics")
data class KafkaTopicsProperties@ConstructorBinding constructor(
    val notificationEmailTopic: String
)
