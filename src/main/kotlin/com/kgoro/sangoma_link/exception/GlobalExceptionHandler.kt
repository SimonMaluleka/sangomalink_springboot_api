package com.kgoro.sangoma_link.exception

import io.jsonwebtoken.JwtException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<ApiError> {
        val apiError = ApiError(ex.localizedMessage, HttpStatus.FORBIDDEN)
        return ResponseEntity(apiError, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationException(e: AuthenticationException): ResponseEntity<ApiError> {
        val apiError = ApiError(e.localizedMessage, HttpStatus.UNAUTHORIZED)
        return ResponseEntity(apiError, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(JwtException::class)
    fun handleJwtException(e: JwtException): ResponseEntity<ApiError> {
        val apiError = ApiError(e.localizedMessage, HttpStatus.UNAUTHORIZED)
        return ResponseEntity(apiError, HttpStatus.UNAUTHORIZED)
    }
}

// Lombok's @Data is replaced by a Kotlin data class
data class ApiError(
    val error: String,
    val status: HttpStatus,
    val timeStamp: LocalDateTime = LocalDateTime.now() // Default value for the timestamp
)
