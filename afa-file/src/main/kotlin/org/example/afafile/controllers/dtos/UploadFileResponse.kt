package org.example.afafile.controllers.dtos

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class UploadFileResponse(

    @Schema(description = "Путь к файлу")
    val path: String
)
