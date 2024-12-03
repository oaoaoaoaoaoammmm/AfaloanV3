package org.example.afaprocess.services

import org.assertj.core.api.Assertions.assertThat
import org.example.afaprocess.exceptions.InternalException
import org.example.afaprocess.models.Process
import org.example.afaprocess.models.enumerations.ProcessStatus
import org.example.afaprocess.repositories.ProcessRepository
import org.example.afaprocess.services.clients.AfaOrderClient
import org.example.afaprocess.utils.createMicroloanDto
import org.example.afaprocess.utils.createOrderDto
import org.example.afaprocess.utils.createProcess
import org.example.afaprocess.utils.createProfileDto
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import reactor.kotlin.test.expectError
import reactor.test.StepVerifier
import java.math.BigDecimal
import java.util.*

class ProcessServiceTest {

    private val processRepository = mock<ProcessRepository>()
    private val afaOrderClient = mock<AfaOrderClient>()

    private val processService = ProcessService(afaOrderClient, processRepository)

    @Test
    fun `find should execute successfully`() {
        val process = createProcess()
        whenever(processRepository.findProcessById(any())).thenReturn(process)

        StepVerifier.create(processService.find(process.id!!))
            .expectNext(process)
            .expectComplete()
            .verify()
    }

    @Test
    fun `find should throw PROCESS_NOT_FOUND`() {
        whenever(processRepository.findProcessById(any())).thenReturn(null)

        StepVerifier.create(processService.find(UUID.randomUUID()))
            .expectError(InternalException::class)
            .verify()
    }

    @Test
    fun `findPage should execute successfully`() {
        val processes = listOf(createProcess(), createProcess(), createProcess())
        whenever(processRepository.findAll(any<Pageable>())).thenReturn(PageImpl(processes))

        StepVerifier.create(processService.findPage(Pageable.ofSize(10)))
            .expectNext(PageImpl(processes))
            .expectComplete()
            .verify()
    }

    @Test
    fun `create should execute successfully`() {
        val process = createProcess()
        whenever(processRepository.save(any<Process>())).thenReturn(process)

        StepVerifier.create(processService.create(process))
            .expectNext(process.id)
            .expectComplete()
            .verify()
    }

    @Test
    fun `update should execute successfully`() {
        val oldProcess = createProcess()
        val newProcess = oldProcess.copy(debt = BigDecimal.ZERO, status = ProcessStatus.CLOSED)
        whenever(processRepository.findProcessById(any())).thenReturn(oldProcess)
        whenever(processRepository.save(any<Process>())).thenReturn(newProcess)

        StepVerifier.create(processService.update(oldProcess.id!!, newProcess))
            .expectNext(newProcess)
            .expectComplete()
            .verify()
    }

    @Test
    fun `calculateDebt should execute successfully`() {
        val process = createProcess()
        val order = createOrderDto()
        val microloan = createMicroloanDto()
        val profile = createProfileDto()
        whenever(processRepository.findAllByStatus(any())).thenReturn(listOf(process))
        whenever(afaOrderClient.findOrder(any())).thenReturn(order)
        whenever(afaOrderClient.findMicroloan(any())).thenReturn(microloan)
        whenever(afaOrderClient.findProfile(any())).thenReturn(profile)
        val pairs = processService.calculateDebt()

        assertThat(pairs.size).isEqualTo(1)
        pairs.forEach { pair ->
            assertThat(pair.first).isEqualTo(profile)
            assertThat(pair.second.debt).isEqualTo(BigDecimal.valueOf(10.0))
        }
    }
}