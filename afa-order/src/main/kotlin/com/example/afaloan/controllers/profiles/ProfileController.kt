package com.example.afaloan.controllers.profiles

import com.example.afaloan.controllers.profiles.ProfileController.Companion.ROOT_URI
import com.example.afaloan.controllers.profiles.dtos.CreateProfileRequest
import com.example.afaloan.controllers.profiles.dtos.CreateProfileResponse
import com.example.afaloan.controllers.profiles.dtos.ProfileDto
import com.example.afaloan.controllers.profiles.dtos.UpdateProfileRequest
import com.example.afaloan.exceptions.Error
import com.example.afaloan.mappers.ProfileMapper
import com.example.afaloan.services.ProfileService
import com.example.afaloan.utils.logger
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping(ROOT_URI)
class ProfileController(
    private val profileMapper: ProfileMapper,
    private val profileService: ProfileService
) {

    @Operation(summary = "Поиск профиля")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Профиль найден",
                content = [Content(schema = Schema(implementation = ProfileDto::class))]
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
    fun find(@PathVariable id: UUID): ProfileDto {
        logger.trace { "Find profile by id - $id" }
        val profile = profileService.find(id)
        return profileMapper.convert(profile)
    }

    @Operation(summary = "Создание профиля")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Профиль создан",
                content = [Content(schema = Schema(implementation = CreateProfileResponse::class))]
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
    fun create(@Valid @RequestBody request: CreateProfileRequest): CreateProfileResponse {
        logger.trace { "Create profile for ${request.name} ${request.surname}" }
        val profile = profileMapper.convert(request)
        val profileId = profileService.create(profile)
        return CreateProfileResponse(profileId)
    }

    @Operation(summary = "Обноление профиля")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Профиль обновлен",
                content = [Content(schema = Schema(implementation = ProfileDto::class))]
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
    fun update(
        @PathVariable id: UUID,
        @Valid @RequestBody request: UpdateProfileRequest
    ): ProfileDto {
        logger.trace { "Update profile by id - $id" }
        val newProfile = profileMapper.convert(request)
        val updatedProfile = profileService.update(id, newProfile)
        return profileMapper.convert(updatedProfile)
    }

    companion object {
        const val ROOT_URI = "/profiles"
    }
}