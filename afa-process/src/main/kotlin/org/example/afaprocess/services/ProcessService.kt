package org.example.afaprocess.services

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.example.afaprocess.exceptions.ErrorCode
import org.example.afaprocess.exceptions.InternalException
import org.example.afaprocess.models.Process
import org.example.afaprocess.models.enumerations.ProcessStatus
import org.example.afaprocess.repositories.ProcessRepository
import org.example.afaprocess.services.clients.AfaOrderClient
import org.example.afaprocess.utils.logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import java.math.BigDecimal
import java.util.*

@Service
class ProcessService(
    private val afaOrderClient: AfaOrderClient,
    private val processRepository: ProcessRepository
) {

    fun find(id: UUID): Mono<Process> {
        logger.info { "Finding process by id - $id" }
        return Mono.fromCallable<Process?> { processRepository.findProcessById(id) }
            .switchIfEmpty(
                Mono.error(
                    InternalException(errorCode = ErrorCode.PROCESS_NOT_FOUND, httpStatus = HttpStatus.NOT_FOUND)
                )
            )
            .subscribeOn(Schedulers.boundedElastic())
    }

    fun findPage(pageable: Pageable): Mono<Page<Process>> {
        logger.info { "Finding process by page № - ${pageable.pageNumber}" }
        return Mono.fromCallable { processRepository.findAll(pageable) }
            .subscribeOn(Schedulers.boundedElastic())
    }

    fun create(process: Process): Mono<UUID> {
        logger.info { "Creating process" }
        return Mono.fromCallable { processRepository.save(process).id!! }
            .subscribeOn(Schedulers.boundedElastic())
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun update(id: UUID, process: Process): Mono<Process> {
        logger.info { "Updating process by id - $id" }
        return find(id).map { process.copy(id = it.id) }
            .flatMap {
                return@flatMap Mono.fromCallable { processRepository.save(it) }
                    .subscribeOn(Schedulers.boundedElastic())
            }
    }


    /**
     * Calculates interest on the process with status ProcessStatus.IN_PROCESSING once a month
     *
     * @author Daniil Afanasev
     */
    @Transactional
    @Scheduled(cron = "\${api.job.scheduler.process-debt}")
    @SchedulerLock(name = "calculateMonthlyInterest")
    fun calculateMonthlyInterest() {
        logger.info { "Calculating process debt" }
        val processes = processRepository.findAllByStatus(ProcessStatus.IN_PROCESSING)
        val calculateProcess =
            processes.map {
                val order = afaOrderClient.findOrder(it.bidId!!)
                val microloan = afaOrderClient.findMicroloan(order.microloanId)
                it.copy(
                    debt = it.debt.multiply(
                        BigDecimal.ONE.plus(microloan.monthlyInterest.divide(BigDecimal.valueOf(PERCENTS_100)))
                    )
                )
            }
        processRepository.saveAll(calculateProcess)
        logger.info { "Calculated process successfully completed" }
    }

    private companion object {
        const val PERCENTS_100: Long = 100
    }
}