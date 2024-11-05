package org.example.afaprocess.exceptions

import org.example.afaprocess.utils.logger
import org.springframework.http.ResponseEntity
import java.util.*

data class Error(
    val errorId: String = UUID.randomUUID().toString(),
    val status: Int,
    val code: ErrorCode,
)

object ErrorUtil {
    fun Error.asResponseEntity(): ResponseEntity<Error> {
        logger.trace { "Return error $this" }
        return ResponseEntity.status(this.status)
            .body(this)
    }
}