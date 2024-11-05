package org.example.afaprocess.exceptions

enum class ErrorCode {

    // common errors
    INVALID_REQUEST,
    SERVICE_UNAVAILABLE,

    // user errors
    FORBIDDEN,

    // process errors
    PROCESS_NOT_FOUND
}