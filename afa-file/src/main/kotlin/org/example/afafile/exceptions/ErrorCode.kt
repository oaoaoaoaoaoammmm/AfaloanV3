package org.example.afafile.exceptions

enum class ErrorCode {

    // common errors
    INVALID_REQUEST,
    SERVICE_UNAVAILABLE,

    // file errors
    WRONG_FILE,
    DOCUMENT_NOT_YOURS,
    UNAVAILABLE_CONTENT_TYPE,
    FILE_SERVICE_UNAVAILABLE,

    // user errors
    FORBIDDEN,
    USER_NOT_FOUND,
    USER_ALREADY_EXIST,
    USER_PASSWORD_INCORRECT,
    ROLE_NOT_FOUND,
}