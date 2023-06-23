package io.directional.wine.exception

import org.springframework.http.HttpStatus

data class ErrorResponse(
    val code: Int,
    val message: String,
    val httpStatus: HttpStatus,
) {
    constructor(e: ErrorCode) : this(e.code, e.message, e.httpStatus)
}