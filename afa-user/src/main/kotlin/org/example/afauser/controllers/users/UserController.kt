package org.example.afauser.controllers.users

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.example.afauser.controllers.users.UserController.Companion.ROOT_URI
import org.example.afauser.controllers.users.dtos.UpdateRoleRequest
import org.example.afauser.controllers.users.dtos.UserDto
import org.example.afauser.mappers.UserMapper
import org.example.afauser.services.UserService
import org.example.afauser.utils.logger
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestParam
import reactor.core.publisher.Mono
import java.util.*
import org.example.afauser.exceptions.Error as Error1

@Tag(name = "User controller", description = "Контроллер для работы с пользователями")
@RestController
@RequestMapping(ROOT_URI)
class UserController(
    private val userMapper: UserMapper,
    private val userService: UserService
) {

    @Operation(summary = "Поиск пользователя по id")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Пользователь найден",
                content = [Content(schema = Schema(implementation = UserDto::class))]
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
    fun find(@PathVariable id: UUID): Mono<UserDto> {
        logger.trace { "Find user by id - $id" }
        return userService.find(id).map(userMapper::convert)
    }

    @Operation(summary = "Проверка существования пользователя по логину")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Пользователь существует",
                content = [Content(schema = Schema(implementation = UserDto::class))]
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
    fun exists(@RequestParam username: String): Mono<Boolean> {
        logger.trace { "Exists user by username: $username" }
        return userService.isExists(username)
    }

    @Operation(summary = "Удаление пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Пользователь удален",
                content = [Content(schema = Schema(implementation = UserDto::class))]
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
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: UUID): Mono<Void> {
        logger.trace { "Delete user with id - $id" }
        return userService.delete(id)
    }

    @Operation(summary = "Обновление ролей пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Роли изменены",
                content = [Content(schema = Schema(implementation = UserDto::class))]
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
    @PatchMapping("/{id}/$ROLES")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SUPERVISOR')")
    fun updateRole(
        @PathVariable id: UUID,
        @Valid @RequestBody request: Mono<UpdateRoleRequest>
    ): Mono<Void> {
        return request.flatMap { newRole ->
            logger.trace { "Update roles for user with id - $id" }
            userService.updateRoles(id, newRole.role).let { Mono.empty() }
        }
    }

    @Operation(summary = "Блокировка пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Заблокирован",
                content = [Content(schema = Schema(implementation = UserDto::class))]
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
    @PatchMapping("/{id}/$BLOCK")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'WORKER')")
    fun block(@PathVariable id: UUID): Mono<Void> {
        logger.trace { "Block user by id - $id" }
        return userService.block(id).let { Mono.empty() }
    }

    @Operation(summary = "Разблокировка пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Разблокирован",
                content = [Content(schema = Schema(implementation = UserDto::class))]
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
    @DeleteMapping("/{id}/$BLOCK")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'WORKER')")
    fun unblock(@PathVariable id: UUID): Mono<Void> {
        logger.trace { "Unblock user with id - $id" }
        return userService.unblock(id).let { Mono.empty() }
    }

    @Operation(summary = "Подтверждение пользователя")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Подтвержден",
                content = [Content(schema = Schema(implementation = UserDto::class))]
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
    @PatchMapping("/{id}/$CONFIRM")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyAuthority('SUPERVISOR', 'WORKER')")
    fun confirm(@PathVariable id: UUID): Mono<Void> {
        logger.trace { "Confirm user with id - $id" }
        return userService.confirm(id).let { Mono.empty() }
    }

    companion object {
        const val ROOT_URI = "/users"
        const val ROLES = "roles"
        const val BLOCK = "block"
        const val CONFIRM = "confirm"
    }
}