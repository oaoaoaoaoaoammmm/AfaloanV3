package com.example.afaloan.controllers.boilingpoints

import com.example.afaloan.controllers.boilingpoints.BoilingPointController.Companion.ROOT_URI
import com.example.afaloan.controllers.boilingpoints.dtos.CreateBoilingPointRequest
import com.example.afaloan.controllers.boilingpoints.dtos.CreateBoilingPointResponse
import com.example.afaloan.controllers.boilingpoints.dtos.UpdateBoilingPointRequest
import com.example.afaloan.exceptions.Error
import com.example.afaloan.mappers.BoilingPointMapper
import com.example.afaloan.models.BoilingPoint
import com.example.afaloan.services.BoilingPointService
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
class BoilingPointController(
    private val boilingPointMapper: BoilingPointMapper,
    private val boilingPointService: BoilingPointService
) {

    @Operation(summary = "Поиск точек кипения")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Точки найдены",
                content = [Content(schema = Schema(implementation = BoilingPoint::class))]
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
    @GetMapping
    @PageableAsQueryParam
    @ResponseStatus(HttpStatus.OK)
    fun findAll(
        @Schema(hidden = true) @PageableDefault(size = DEFAULT_PAGE_SIZE) pageable: Pageable
    ): Page<BoilingPoint> {
        logger.trace { "Find boiling point's page" }
        return boilingPointService.findPage(pageable)
    }

    @Operation(summary = "Поиск точки кипения")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Точка найдена",
                content = [Content(schema = Schema(implementation = BoilingPoint::class))]
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
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun find(@PathVariable id: UUID): BoilingPoint {
        logger.trace { "Find boiling point by id - $id" }
        return boilingPointService.find(id)
    }

    @Operation(summary = "Создание точки кипения")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Точка создана",
                content = [Content(schema = Schema(implementation = CreateBoilingPointResponse::class))]
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
    fun create(@Valid @RequestBody request: CreateBoilingPointRequest): CreateBoilingPointResponse {
        logger.trace { "Create boiling point with address - ${request.address}" }
        val boilingPoint = boilingPointMapper.convert(request)
        return CreateBoilingPointResponse(boilingPointService.create(boilingPoint))
    }

    @Operation(summary = "Обновление точки кипения")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Точка создана",
                content = [Content(schema = Schema(implementation = BoilingPoint::class))]
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
                description = "Не найдено",
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
        @Valid @RequestBody request: UpdateBoilingPointRequest
    ): BoilingPoint {
        logger.trace { "Update boiling point by id - $id" }
        val boilingPoint = boilingPointMapper.convert(request)
        return boilingPointService.update(id, boilingPoint)
    }

    @Operation(summary = "Удаление точки кипения")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Точка удалена",
                content = [Content(schema = Schema(implementation = Void::class))]
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
        logger.trace { "Delete boiling point by id - $id" }
        boilingPointService.delete(id)
    }

    companion object {
        const val ROOT_URI = "/boiling-points"
        const val DEFAULT_PAGE_SIZE = 50
    }
}