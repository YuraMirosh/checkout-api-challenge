package com.maha.checkout.infrastructure.adapter.inbound.http

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {

    @ExceptionHandler(Throwable::class)
    suspend fun handleThrowable(e: Throwable): ResponseEntity<Any> =
        when (e) {
            is NotFoundException -> ResponseEntity.notFound()
            else -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        }
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PROBLEM_JSON_VALUE)
            .build()
}

class NotFoundException(message: String) : RuntimeException(message)
