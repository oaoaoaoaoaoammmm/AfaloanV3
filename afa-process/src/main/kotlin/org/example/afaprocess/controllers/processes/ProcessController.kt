package org.example.afaprocess.controllers.processes

import org.example.afaprocess.controllers.processes.dtos.CreateProcessRequest
import org.example.afaprocess.controllers.processes.dtos.CreateProcessResponse
import org.example.afaprocess.controllers.processes.dtos.ProcessView
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.Valid
import org.example.afaprocess.controllers.processes.ProcessController.Companion.ROOT_URI
import org.example.afaprocess.controllers.processes.dtos.ProcessDto
import org.example.afaprocess.mappers.ProcessMapper
import org.example.afaprocess.services.ProcessService
import org.example.afaprocess.utils.logger
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.PutMapping


import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping(ROOT_URI)
class ProcessController(
    private val processMapper: ProcessMapper,
    private val processService: ProcessService
) {

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: UUID): Mono<ProcessDto> {
        logger.trace { "Find process by id - $id" }
        return processService.find(id).map(processMapper::convertToDto)
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findPage(
        @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) pageable: Pageable
    ): Mono<Page<ProcessView>> {
        logger.trace { "Find page process's with - № ${pageable.pageNumber}" }
        return processService.findPage(pageable).map { it.map(processMapper::convertToView) }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'WORKER')")
    fun create(@Valid @RequestBody request: Mono<CreateProcessRequest>): Mono<CreateProcessResponse> {
        logger.trace { "Create process request" }
        return request.map(processMapper::convert)
            .flatMap { processService.create(it) }
            .map { CreateProcessResponse(it) }
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'WORKER')")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody dto: Mono<ProcessDto>
    ): Mono<ProcessDto> {
        logger.trace { "Update process by id - $id" }
        return dto.map(processMapper::convert)
            .flatMap { processService.update(id, it) }
            .map(processMapper::convertToDto)
    }

    companion object {
        const val ROOT_URI = "/processes"
        const val DEFAULT_PAGE_SIZE = 50
    }
}