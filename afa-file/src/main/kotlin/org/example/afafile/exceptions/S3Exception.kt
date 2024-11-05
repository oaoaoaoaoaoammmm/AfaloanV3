package org.example.afafile.exceptions

class S3Exception(
    override val cause: Throwable,
    override val message: String
) : RuntimeException(message, cause)