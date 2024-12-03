package org.example.afaprocess.services

import org.example.afaprocess.kafka.KafkaNotificationProducer
import org.example.afaprocess.utils.logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class InterestScheduler(
    private val processService: ProcessService,
    private val templateService: TemplateService,
    private val kafkaNotificationProducer: KafkaNotificationProducer
) {

    @Scheduled(cron = "\${api.job.scheduler.process-debt}")
    fun calculateMountInterest() {
        val pairs = processService.calculateDebt()
        logger.info { "Sending email notifications" }
        pairs.forEach {
            val emailNotification = templateService.convertToEmailMessage(it)
            kafkaNotificationProducer.sendEmailNotification(emailNotification)
        }
        logger.info { "Email notifications sent" }
    }
}