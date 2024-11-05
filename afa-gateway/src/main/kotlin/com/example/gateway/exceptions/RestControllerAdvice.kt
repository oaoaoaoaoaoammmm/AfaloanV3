package com.example.gateway.exceptions

import com.example.gateway.exceptions.ErrorUtil.asResponseEntity
import com.example.gateway.utils.logger
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.reactive.resource.NoResourceFoundException
import org.springframework.web.server.ServerWebInputException

@ControllerAdvice
class RestControllerAdvice {

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

    @ExceptionHandler(NoResourceFoundException::class)
    fun resourcesNotFoundExceptions(exception: Exception): ResponseEntity<Error> {
        logger.error(exception) { "Handle validation error" }
        return Error(status = HttpStatus.NOT_FOUND.value(), code = ErrorCode.RESOURCES_NOT_FOUND)
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
}