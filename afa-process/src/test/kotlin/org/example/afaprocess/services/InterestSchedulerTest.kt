package org.example.afaprocess.services

import org.example.afaprocess.kafka.KafkaNotificationProducer
import org.example.afaprocess.utils.createEmailNotification
import org.example.afaprocess.utils.createProcess
import org.example.afaprocess.utils.createProfileDto
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class InterestSchedulerTest {

    private val processService = mock<ProcessService>()
    private val templateService = mock<TemplateService>()
    private val kafkaNotificationProducer = mock<KafkaNotificationProducer>()

    private val interestScheduler = InterestScheduler(processService, templateService, kafkaNotificationProducer)

    @Test
    fun `calculateMountInterest should execute successfully`() {
        val email = createEmailNotification()
        val pairs = listOf(Pair(createProfileDto(), createProcess()))
        whenever(processService.calculateDebt()).thenReturn(pairs)
        whenever(templateService.convertToEmailMessage(any())).thenReturn(email)

        interestScheduler.calculateMountInterest()
    }
}