package com.kgoro.sangoma_link.auth

data class AuthenticationResponse(
    val token: String,
    val refreshJwtToken: String
)
