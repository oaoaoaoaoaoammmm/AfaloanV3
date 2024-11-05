package org.example.afaprocess.controllers

import org.assertj.core.api.Assertions.assertThat
import org.example.afaprocess.BaseIntegrationTest
import org.example.afaprocess.controllers.processes.dtos.CreateProcessResponse
import org.example.afaprocess.controllers.processes.dtos.ProcessDto
import org.example.afaprocess.models.Process
import org.example.afaprocess.models.enumerations.ProcessStatus
import org.example.afaprocess.utils.createCreateProcessRequest
import org.example.afaprocess.utils.createProcess
import org.example.afaprocess.utils.createProcessDto
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType

class ProcessControllerTest : BaseIntegrationTest() {

    @Test
    fun `create should return CREATED`() {
        createProcessForTest()
    }

    @Test
    fun `find should return OK`() {
        val process = createProcessForTest()
        client.get()
            .uri("/processes/${process.id}")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectAll(
                { it.expectStatus().isOk },
                {
                    it.expectBody(ProcessDto::class.java).value { dto ->
                        assertThat(dto.comment).isEqualTo(process.comment)
                        assertThat(dto.status).isEqualTo(process.status)
                    }
                }
            )
    }

    @Test
    fun `findPage should return OK`() {
        client.get()
            .uri("/processes?page=0&size=20")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectAll(
                { it.expectStatus().isOk },
                { it.expectBody().jsonPath("$.content[0].id").isNotEmpty },
                { it.expectBody().jsonPath("$.content[0].debt").isNotEmpty },
                { it.expectBody().jsonPath("$.content[0].comment").isNotEmpty },
                { it.expectBody().jsonPath("$.content[0].status").isNotEmpty }
            )
    }

    fun createProcessForTest(): Process {
        val processReq = createCreateProcessRequest()
        var process = createProcess()
        client.post()
            .uri("/processes")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(processReq)
            .exchange()
            .expectAll(
                { it.expectStatus().isCreated },
                {
                    it.expectBody(CreateProcessResponse::class.java).value { newProcess ->
                        assertThat(newProcess.id).isNotNull
                        process = process.copy(id = newProcess.id, status = ProcessStatus.CREATED)
                    }
                },
            )
        return process
    }

    @Test
    fun `update should return OK`() {
        val process = createProcessForTest()
        client.put()
            .uri("/processes/${process.id}")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(createProcessDto())
            .exchange()
            .expectAll(
                { it.expectStatus().isOk },
                { it.expectBody().jsonPath("$.debt").isNotEmpty },
                { it.expectBody().jsonPath("$.comment").isNotEmpty },
                { it.expectBody().jsonPath("$.status").isNotEmpty },
                { it.expectBody().jsonPath("$.bidId").isNotEmpty },
            )
    }
}