package com.kgoro.sangoma_link.auth

import com.kgoro.sangoma_link.access_token.AccessToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository

@Repository
interface RefreshTokenRepository: JpaRepository<AccessToken, Long> {
    abstract fun findUserDetailsByAccessToken(accessToken: String): UserDetails?
}