package com.example.afaloan.exceptions

import com.example.afaloan.exceptions.ErrorUtil.asResponseEntity
import com.example.afaloan.utils.logger
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebInputException

@ControllerAdvice
class RestControllerAdvice {

    @ApiResponse(responseCode = "500", useReturnTypeSchema = true)
    @ExceptionHandler(Exception::class)
    fun defaultHandler(ex: Exception): ResponseEntity<Error> {
        logger.error(ex) { "Handle default error" }
        return Error(status = HttpStatus.INTERNAL_SERVER_ERROR.value(), code = ErrorCode.SERVICE_UNAVAILABLE)
            .asResponseEntity()
    }

    @ExceptionHandler(InternalException::class)
    fun internalException(ex: InternalException): ResponseEntity<Error> {
        logger.error(ex) { "Internal error" }
        return Error(status = ex.httpStatus.value(), code = ex.errorCode)
            .asResponseEntity()
    }

    @ExceptionHandler(
        value = [
            ConstraintViolationException::class,
            MethodArgumentNotValidException::class,
            WebExchangeBindException::class,
            ServerWebInputException::class
        ]
    )
    fun validationExceptions(exception: Exception): ResponseEntity<Error> {
        logger.error(exception) { "Handle validation error" }
        return Error(status = HttpStatus.BAD_REQUEST.value(), code = ErrorCode.INVALID_REQUEST)
            .asResponseEntity()
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun authorizationDeniedException(ex: AccessDeniedException): ResponseEntity<Error> {
        logger.error(ex) { "Access denied" }
        return Error(status = HttpStatus.FORBIDDEN.value(), code = ErrorCode.FORBIDDEN)
            .asResponseEntity()
    }
}