package com.example.afaloan.exceptions

enum class ErrorCode {

    // common errors
    INVALID_REQUEST,
    SERVICE_UNAVAILABLE,

    // user errors
    FORBIDDEN,

    // profile errors
    PROFILE_NOT_FOUND,

    // boiling point errors
    BOILING_POINT_NOT_FOUND,

    // microloan errors
    MICROLOAN_NOT_FOUND,

    // bid errors
    BID_NOT_FOUND
}