package io.directional.wine.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice(annotations = [RestController::class])
class GlobalExceptionHandler {

    @ExceptionHandler(ClientException::class)
    fun handleException(e: ClientException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(e.errorCode)
        return ResponseEntity.status(e.errorCode.httpStatus).body(errorResponse)
    }
}

