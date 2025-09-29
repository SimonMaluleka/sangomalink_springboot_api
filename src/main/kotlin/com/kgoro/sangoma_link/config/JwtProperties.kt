package com.kgoro.sangoma_link.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "application.security.jwt")
data class JwtProperties(
    val secretKey: String,
    val accessTokenExpiration: Long ,
    val refreshTokenExpiration: Long ,

)
