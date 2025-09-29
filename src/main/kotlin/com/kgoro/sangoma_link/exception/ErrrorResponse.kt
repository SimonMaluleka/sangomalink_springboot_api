package com.kgoro.sangoma_link.exception

data class ErrorResponse(
    val message: String,
    val timestamp: java.time.LocalDateTime = java.time.LocalDateTime.now()
)
