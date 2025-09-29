package com.kgoro.sangoma_link.security

data class JwtTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)

data class TokenPair(
    val accessToken: String,
    val refreshToken: String,
    val expiresIn: Long
)
