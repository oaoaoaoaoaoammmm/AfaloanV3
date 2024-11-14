package org.example.afauser.controllers.auth

import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.example.afauser.controllers.auth.AuthController.Companion.ROOT_URI
import org.example.afauser.controllers.auth.dtos.AuthorizeUserRequest
import org.example.afauser.controllers.auth.dtos.AuthorizationDetails
import org.example.afauser.controllers.auth.dtos.AuthorizeUserResponse
import org.example.afauser.controllers.auth.dtos.RegisterUserRequest
import org.example.afauser.controllers.auth.dtos.RegisterUserResponse
import org.example.afauser.mappers.AuthMapper
import org.example.afauser.services.AuthService
import org.example.afauser.utils.logger
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

import reactor.core.publisher.Mono
import org.example.afauser.exceptions.Error as Error1

@Tag(name = "Auth controller", description = "Контроллер для работы с авторизацией и аутентификацией")
@RestController
@RequestMapping(ROOT_URI)
class AuthController(
    private val authMapper: AuthMapper,
    private val authService: AuthService,
) {

    @Operation(summary = "Регистрация пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Пользователь создан",
                content = [Content(schema = Schema(implementation = RegisterUserResponse::class))]
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
    @PreAuthorize("hasAnyAuthority('SUPERVISOR')")
    fun register(@Valid @RequestBody request: Mono<RegisterUserRequest>): Mono<RegisterUserResponse> {
        return request.map(authMapper::convert)
            .flatMap { user ->
                logger.trace { "Register user with username - ${user.username}" }
                authService.register(user)
            }.map { RegisterUserResponse(it) }
    }

    @Hidden
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("isAuthenticated()")
    fun getAuthDetails(): Mono<AuthorizationDetails> {
        logger.trace { "Get authorization details for user" }
        return authService.getAuthDetails()
    }

    @Operation(summary = "Получение токенов")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Токены созданы",
                content = [Content(schema = Schema(implementation = AuthorizeUserResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error1::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error1::class))]
            )
        ]
    )
    @PostMapping(TOKENS)
    @ResponseStatus(HttpStatus.CREATED)
    fun authorize(@Valid @RequestBody request: Mono<AuthorizeUserRequest>): Mono<AuthorizeUserResponse> {
        return request.map(authMapper::convert)
            .flatMap { auth ->
                logger.trace { "Authorization user with username - ${auth.username}" }
                authService.authorize(auth)
            }
    }

    @Operation(summary = "Обновление токенов")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Токены созданы",
                content = [Content(schema = Schema(implementation = AuthorizeUserResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error1::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error1::class))]
            )
        ]
    )
    @PostMapping(TOKENS + REFRESH)
    @ResponseStatus(HttpStatus.CREATED)
    fun reAuthorize(@RequestParam refresh: String): Mono<AuthorizeUserResponse> {
        logger.trace { "Reauthorize user" }
        return authService.reAuthorize(refresh)
    }

    companion object {
        const val ROOT_URI = "/auth"
        const val TOKENS = "/tokens"
        const val REFRESH = "/refresh"
    }
}