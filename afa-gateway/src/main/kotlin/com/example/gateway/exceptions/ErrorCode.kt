package com.example.gateway.exceptions

enum class ErrorCode {

    // common errors
    INVALID_REQUEST,
    SERVICE_UNAVAILABLE,
    CIRCUIT_BREAKER_STOP,
    RESOURCES_NOT_FOUND
}