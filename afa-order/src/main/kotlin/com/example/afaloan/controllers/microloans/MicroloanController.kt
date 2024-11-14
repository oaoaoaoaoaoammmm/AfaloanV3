package com.example.afaloan.controllers.microloans

import com.example.afaloan.controllers.microloans.MicroloanController.Companion.ROOT_URI
import com.example.afaloan.controllers.microloans.dtos.CreateMicroloanResponse
import com.example.afaloan.controllers.microloans.dtos.MicroloanDto
import com.example.afaloan.exceptions.Error
import com.example.afaloan.mappers.MicroloanMapper
import com.example.afaloan.models.Microloan
import com.example.afaloan.services.MicroloanService
import com.example.afaloan.utils.logger
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springdoc.core.converters.models.PageableAsQueryParam
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping(ROOT_URI)
class MicroloanController(
    private val microloanMapper: MicroloanMapper,
    private val microloanService: MicroloanService
) {

    @Operation(summary = "Поиск микрозаймов")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Микрозаймы найдены",
                content = [Content(schema = Schema(implementation = Microloan::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Не доступно",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error::class))]
            )
        ]
    )
    @GetMapping(params = ["page"])
    @PageableAsQueryParam
    @ResponseStatus(HttpStatus.OK)
    fun findPage(
        @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) pageable: Pageable
    ): Page<Microloan> {
        logger.trace { "Find microloan's page" }
        return microloanService.findPage(pageable)
    }

    @Operation(summary = "Поиск микрозайма")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Микрозайм найден",
                content = [Content(schema = Schema(implementation = MicroloanDto::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Не доступно",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Не найден",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error::class))]
            )
        ]
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: UUID): MicroloanDto {
        logger.trace { "Find microloan by id - $id" }
        val microloan = microloanService.find(id)
        return microloanMapper.convert(microloan)
    }

    @Operation(summary = "Создание микрозайма")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Микрозайм создан",
                content = [Content(schema = Schema(implementation = CreateMicroloanResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Не доступно",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error::class))]
            )
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'WORKER')")
    fun create(@Valid @RequestBody dto: MicroloanDto): CreateMicroloanResponse {
        logger.trace { "Create microloan" }
        val microloan = microloanMapper.convert(dto)
        val microloanId = microloanService.create(microloan)
        return CreateMicroloanResponse(microloanId)
    }

    @Operation(summary = "Обновление микрозайма")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Микрозайм обновлен",
                content = [Content(schema = Schema(implementation = MicroloanDto::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Не доступно",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Не найден",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error::class))]
            )
        ]
    )
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'WORKER')")
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody dto: MicroloanDto
    ): MicroloanDto {
        logger.trace { "Update microloan by id - $id" }
        val updatedMicroloan = microloanMapper.convert(dto)
        return microloanService.update(id, updatedMicroloan)
            .let { microloanMapper.convert(it) }
    }

    @Operation(summary = "Удаление микрозайма")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Микрозайм удален",
                content = [Content(schema = Schema(implementation = MicroloanDto::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Не доступно",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error::class))]
            )
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'WORKER')")
    fun delete(@PathVariable id: UUID) {
        logger.trace { "Delete microloan by id - $id" }
        microloanService.delete(id)
    }

    companion object {
        const val ROOT_URI = "/microloans"
        const val DEFAULT_PAGE_SIZE = 50
    }
}