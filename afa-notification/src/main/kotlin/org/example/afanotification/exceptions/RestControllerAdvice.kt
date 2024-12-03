package org.example.afanotification.exceptions

import com.fasterxml.jackson.core.JacksonException
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.example.afanotification.utils.logger
import org.springframework.mail.MailException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class RestControllerAdvice {

    @ApiResponse(responseCode = "500", useReturnTypeSchema = true)
    @ExceptionHandler(Exception::class)
    fun defaultHandler(ex: Exception) {
        logger.error(ex) { "Handle default error" }
    }

    @ExceptionHandler(MailException::class)
    fun internalException(ex: MailException) {
        logger.error(ex) { "Mail error" }
    }

    @ExceptionHandler(JacksonException::class)
    fun authorizationDeniedException(ex: JacksonException) {
        logger.error(ex) { "Parse message error" }
    }
}