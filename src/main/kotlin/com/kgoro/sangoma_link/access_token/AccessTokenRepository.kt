package com.kgoro.sangoma_link.access_token


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface AccessTokenRepository: JpaRepository<AccessToken, Long> {
    fun findByAccessToken(token: String): Optional<AccessToken>
    fun findUserDetailsByRefreshAccessToken(token: String): AccessToken
}
