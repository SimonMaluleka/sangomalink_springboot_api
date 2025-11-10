package com.kgoro.sangoma_link.security

import com.fasterxml.jackson.databind.ObjectMapper
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class JwtAuthenticationEntryPoint(private val objectMapper: ObjectMapper) : AuthenticationEntryPoint {

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        val expired = request?.getAttribute("expired")
        if (expired is ExpiredJwtException) {
            val errorResponse = mapOf(
                "status" to HttpStatus.UNAUTHORIZED.value(),
                "error" to "Unauthorized",
                "message" to "Your session has expired. Please log in again."
            )
            response?.status = HttpStatus.UNAUTHORIZED.value()
            response?.contentType = "application/json"
            response?.writer?.write(objectMapper.writeValueAsString(errorResponse))
        } else {
            // Handle other authentication errors if needed
            response?.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
        }
    }
}