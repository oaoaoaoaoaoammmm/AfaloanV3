package org.example.afaprocess.services

import org.assertj.core.api.Assertions.assertThat
import org.example.afaprocess.utils.USERNAME
import org.example.afaprocess.utils.createProcess
import org.example.afaprocess.utils.createProfileDto
import org.junit.jupiter.api.Test

class TemplateServiceTest {

    private val templateService = TemplateService()

    @Test
    fun `convertToEmailMessage should execute successfully`() {
        val pair = Pair(createProfileDto(), createProcess())

        val email = templateService.convertToEmailMessage(pair)

        assertThat(email.email).isEqualTo(USERNAME)
        assertThat(email.title).isEqualTo("Напоминание о нашем общем благе благе")
    }
}