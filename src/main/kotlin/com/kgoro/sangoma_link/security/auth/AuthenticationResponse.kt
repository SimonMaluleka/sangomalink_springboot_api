package com.kgoro.sangoma_link.security.auth

data class AuthenticationResponse(
    val token: String,
    val refreshJwtToken: String
)
