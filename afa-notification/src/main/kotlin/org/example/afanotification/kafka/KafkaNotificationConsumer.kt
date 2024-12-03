package org.example.afanotification.kafka

import org.example.afanotification.services.EmailSender
import org.example.afanotification.utils.logger
import org.example.afanotification.utils.toObject
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaNotificationConsumer(
    private val emailSender: EmailSender
) {

    @KafkaListener(
        topics = ["\${spring.kafka.topics.notification-email-topic}"],
        groupId = "\${spring.kafka.consumer.group-id}"
    )
    fun consume(emailNotification: String) {
        logger.info { "Received email - $emailNotification" }
        emailSender.sendEmail(emailNotification.toObject())
    }
}