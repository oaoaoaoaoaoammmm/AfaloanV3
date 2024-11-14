package org.example.afafile.controllers.dtos

import io.swagger.v3.oas.annotations.media.Schema

@Schema
data class FindFileUrlResponse(

    @Schema(description = "Ссылка для файла")
    val url: String
)