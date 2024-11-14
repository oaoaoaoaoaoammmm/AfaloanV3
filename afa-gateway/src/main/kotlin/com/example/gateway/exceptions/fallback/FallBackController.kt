package com.example.gateway.exceptions.fallback

import com.example.gateway.exceptions.ErrorCode
import com.example.gateway.exceptions.InternalException
import com.example.gateway.exceptions.fallback.FallBackController.Companion.ROOT_URI
import io.swagger.v3.oas.annotations.Hidden
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod.GET
import org.springframework.web.bind.annotation.RequestMethod.HEAD
import org.springframework.web.bind.annotation.RequestMethod.POST
import org.springframework.web.bind.annotation.RequestMethod.PUT
import org.springframework.web.bind.annotation.RequestMethod.PATCH
import org.springframework.web.bind.annotation.RequestMethod.DELETE
import org.springframework.web.bind.annotation.RequestMethod.OPTIONS
import org.springframework.web.bind.annotation.RequestMethod.TRACE

import org.springframework.web.bind.annotation.RestController

@Hidden
@RestController
@RequestMapping(ROOT_URI)
class FallBackController {

    @RequestMapping(method = [GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE])
    fun fallback() {
        throw InternalException(httpStatus = HttpStatus.SERVICE_UNAVAILABLE, errorCode = ErrorCode.CIRCUIT_BREAKER_STOP)
    }

    companion object {
        const val ROOT_URI = "/fallback"
    }
}