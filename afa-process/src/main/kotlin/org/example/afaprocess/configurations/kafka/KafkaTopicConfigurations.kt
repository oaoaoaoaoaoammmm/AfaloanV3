package org.example.afaprocess.configurations.kafka

import org.apache.kafka.clients.admin.NewTopic
import org.example.afaprocess.configurations.kafka.properties.KafkaTopicsProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder

@Configuration
class KafkaTopicConfigurations(
    private val kafkaTopicsProperties: KafkaTopicsProperties
) {

    @Bean
    fun notificationEmailTopic(): NewTopic {
        return TopicBuilder.name(kafkaTopicsProperties.notificationEmailTopic)
            .partitions(PARTITIONS)
            .replicas(REPLICAS)
            .configs(mapOf(
                "min.insync.replicas" to SYNC_REPLICAS
            ))
            .build()
    }

    private companion object {
        const val PARTITIONS = 1
        const val REPLICAS = 3
        const val SYNC_REPLICAS = "2"
    }
}