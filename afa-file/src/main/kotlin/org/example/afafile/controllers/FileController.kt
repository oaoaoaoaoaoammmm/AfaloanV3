package org.example.afafile.controllers

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.afafile.controllers.FileController.Companion.ROOT_URI
import org.example.afafile.controllers.dtos.FindFileUrlResponse
import org.example.afafile.controllers.dtos.UploadFileResponse
import org.example.afafile.exceptions.Error
import org.example.afafile.services.FileService
import org.example.afafile.utils.AVAILABLE_DOCUMENT_TYPES
import org.example.afafile.utils.SecurityContext
import org.example.afafile.utils.logger
import org.springframework.http.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping(ROOT_URI)
@Tag(name = "File controller", description = "Контроллер для работы с файлами")
class FileController(
    private val fileService: FileService
) {

    @Operation(summary = "Загрузка файла")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "201",
                description = "Файл загружен",
                content = [Content(schema = Schema(implementation = UploadFileResponse::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Недоступно",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error::class))]
            )
        ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(
        @Parameter(description = "Файл к загрузке") @RequestParam file: MultipartFile
    ): UploadFileResponse {
        logger.trace { "Upload document request" }
        val path = fileService.upload(file, AVAILABLE_DOCUMENT_TYPES)
        return UploadFileResponse(path)
    }

    @Operation(summary = "Скачивание файла")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Файл скачан"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Недоступно",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Файл не найден",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error::class))]
            )
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_OCTET_STREAM_VALUE])
    fun download(
        @Parameter(description = "Путь до файла") @RequestParam path: String,
        @Parameter(description = "Id пользователя") @RequestParam(required = false)
        userId: String = SecurityContext.getAuthorizedUserId().toString()
    ): ResponseEntity<ByteArray> {
        logger.trace { "Download document by path - $path" }
        return fileService.download(path, userId)
            .asFileResponseEntity(path.split("/").last())
    }

    @Operation(summary = "Удаление файла")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "204",
                description = "Файл удален"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Недоступно",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Файл не найден",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error::class))]
            )
        ]
    )
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(
        @Parameter(description = "Путь до файла") @RequestParam path: String
    ) {
        logger.trace { "Delete document by path - $path" }
        fileService.delete(path)
    }

    @Operation(summary = "Поиск превью файлов")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Превью найдены"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Недоступно",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Файл не найден",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error::class))]
            )
        ]
    )
    @GetMapping(PREVIEWS)
    @ResponseStatus(HttpStatus.OK)
    fun findPreviews(
        @Parameter(description = "Id пользователя") @RequestParam(required = false)
        userId: String = SecurityContext.getAuthorizedUserId().toString()
    ): Collection<String> {
        logger.trace { "Find document previews" }
        val previews = fileService.findObjectsPreviewsByUser(userId)
        logger.trace { "Found previews - $previews" }
        return previews
    }

    @Operation(summary = "Поиск url файла")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Url найден"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Неверный запрос",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "403",
                description = "Недоступно",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Сервис не доступен",
                content = [Content(schema = Schema(implementation = Error::class))]
            )
        ]
    )
    @GetMapping(URLS)
    @ResponseStatus(HttpStatus.OK)
    fun findDocumentUrl(
        @Parameter(description = "Путь до файла") @RequestParam path: String,
        @Parameter(description = "Id пользователя") @RequestParam(required = false)
        userId: String = SecurityContext.getAuthorizedUserId().toString()
    ): FindFileUrlResponse {
        logger.trace { "Find document url by path - $path" }
        val url = fileService.findObjectUrl(path, userId)
        return FindFileUrlResponse(url)
    }

    private fun ByteArray.asFileResponseEntity(filename: String): ResponseEntity<ByteArray> {
        val contentDisposition: ContentDisposition = ContentDisposition.inline().filename(filename).build()
        val headers = HttpHeaders()
        headers.contentDisposition = contentDisposition
        headers.contentType = MediaType.APPLICATION_OCTET_STREAM
        return ResponseEntity.ok()
            .headers(headers)
            .contentType(MediaType.APPLICATION_OCTET_STREAM)
            .body(this)
    }

    companion object {
        const val ROOT_URI = "/files"
        const val PREVIEWS = "/previews"
        const val URLS = "/urls"
    }
}