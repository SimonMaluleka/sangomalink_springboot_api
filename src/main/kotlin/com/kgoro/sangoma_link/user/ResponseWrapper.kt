package com.kgoro.sangoma_link.user

data class ResponseWrapper<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null,
    val errors: List<ErrorDetail>? = null,
    val timestamp: String = java.time.LocalDateTime.now().toString(),
    val path: String? = null
) {
    companion object {
        fun <T> success(
            data: T? = null,
            message: String? = null
        ): ResponseWrapper<T> {
            return ResponseWrapper(
                success = true,
                data = data,
                message = message
            )
        }

        fun <T> error(
            message: String,
            errors: List<ErrorDetail>? = null
        ): ResponseWrapper<T> {
            return ResponseWrapper(
                success = false,
                message = message,
                errors = errors
            )
        }
    }
}

data class ErrorDetail(
    val field: String? = null,
    val message: String,
    val code: String? = null
)