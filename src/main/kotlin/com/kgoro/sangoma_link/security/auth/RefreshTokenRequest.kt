package com.kgoro.sangoma_link.security.auth

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class RefreshTokenRequest(
    @NotNull
    @NotEmpty
    val token: String
)
