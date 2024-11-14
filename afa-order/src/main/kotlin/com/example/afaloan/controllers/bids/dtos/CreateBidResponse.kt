package com.example.afaloan.controllers.bids.dtos

import io.swagger.v3.oas.annotations.media.Schema
import java.util.UUID

@Schema
data class CreateBidResponse(
    @Schema(description = "Id")
    val id: UUID
)
