package org.example.afaprocess.kafka

import org.example.afaprocess.configurations.kafka.properties.KafkaTopicsProperties
import org.example.afaprocess.kafka.messages.EmailNotification
import org.example.afaprocess.utils.logger
import org.example.afaprocess.utils.toJson
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component

@Component
class KafkaNotificationProducer(
    private val topicProps: KafkaTopicsProperties,
    private val kafkaEmailTemplate: KafkaTemplate<String, String>
) {

    fun sendEmailNotification(email: EmailNotification) {
        logger.info { "Sending message - $email to - ${topicProps.notificationEmailTopic}" }
        kafkaEmailTemplate.send(topicProps.notificationEmailTopic, email.id.toString(), email.toJson())
            .whenComplete { result, ex -> addCallBack(result, ex) }
    }

    private fun <T : SendResult<String, String>> addCallBack(result: T, ex: Throwable?) {
        if (ex == null) {
            logger.info { "Message with id - ${result.producerRecord.key()} sent successfully" }
        } else {
            logger.warn { "Message with id - ${result.producerRecord.key()} sent failed, because - ${ex.message}" }
        }
    }
}
