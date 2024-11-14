package org.example.afafile.exceptions

import io.swagger.v3.oas.annotations.media.Schema
import org.example.afafile.utils.logger
import org.springframework.http.ResponseEntity
import java.util.*

@Schema(description = "Класс ошибки")
data class Error(
    @Schema(description = "Id ошибки")
    val errorId: String = UUID.randomUUID().toString(),
    @Schema(description = "Статус код")
    val status: Int,
    @Schema(description = "Причина")
    val code: ErrorCode,
)

object ErrorUtil {
    fun Error.asResponseEntity(): ResponseEntity<Error> {
        logger.trace { "Return error $this" }
        return ResponseEntity.status(this.status)
            .body(this)
    }
}