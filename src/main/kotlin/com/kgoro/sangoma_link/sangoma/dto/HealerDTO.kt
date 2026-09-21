package com.sangoma.dto

data class HealerRequest(
    val username: String,
    val name: String,
    val email: String
)

data class HealerResponse(
    val id: Long,
    val username: String,
    val name: String,
    val email: String
)
