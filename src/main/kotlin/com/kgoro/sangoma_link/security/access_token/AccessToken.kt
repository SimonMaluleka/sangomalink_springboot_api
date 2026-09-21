package com.kgoro.sangoma_link.security.access_token

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.springframework.security.core.userdetails.UserDetails

@Entity
data class AccessToken (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(length = 2048)
    val accessToken: String,
    @Column(length = 2048)
    val refreshAccessToken: String,
    val userDetails: UserDetails,
    val isLoggedOut: Boolean = false
)