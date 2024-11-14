package org.example.afaprocess.controllers.processes

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import org.example.afaprocess.controllers.processes.dtos.CreateProcessRequest
import org.example.afaprocess.controllers.processes.dtos.CreateProcessResponse
import org.example.afaprocess.controllers.processes.dtos.ProcessView
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
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
import org.example.afaprocess.exceptions.Error as Error1

@Tag(name = "Process controller", description = "Контроллер для работы с процессами")
@RestController
@RequestMapping(ROOT_URI)
class ProcessController(
    private val processMapper: ProcessMapper,
    private val processService: ProcessService
) {

    @Operation(summary = "Поиск процесса")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Процесс найден",
                content = [Content(schema = Schema(implementation = ProcessDto::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error1::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Не доступно",
                content = [Content(schema = Schema(implementation = Error1::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error1::class))]
            )
        ]
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: UUID): Mono<ProcessDto> {
        logger.trace { "Find process by id - $id" }
        return processService.find(id).map(processMapper::convertToDto)
    }

    @Operation(summary = "Поиск страницы процессов")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Процессы найдены",
                content = [Content(schema = Schema(implementation = ProcessView::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error1::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Не доступно",
                content = [Content(schema = Schema(implementation = Error1::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error1::class))]
            )
        ]
    )
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun findPage(
        @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) pageable: Pageable
    ): Mono<Page<ProcessView>> {
        logger.trace { "Find page process's with - № ${pageable.pageNumber}" }
        return processService.findPage(pageable).map { it.map(processMapper::convertToView) }
    }

    @Operation(summary = "Создание процесса")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Процесс создан",
                content = [Content(schema = Schema(implementation = CreateProcessResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error1::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Не доступно",
                content = [Content(schema = Schema(implementation = Error1::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error1::class))]
            )
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'WORKER')")
    fun create(@Valid @RequestBody request: Mono<CreateProcessRequest>): Mono<CreateProcessResponse> {
        logger.trace { "Create process request" }
        return request.map(processMapper::convert)
            .flatMap { processService.create(it) }
            .map { CreateProcessResponse(it) }
    }

    @Operation(summary = "Обновление процесса")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Процесс обновлен",
                content = [Content(schema = Schema(implementation = ProcessDto::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error1::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Не доступно",
                content = [Content(schema = Schema(implementation = Error1::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error1::class))]
            )
        ]
    )
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