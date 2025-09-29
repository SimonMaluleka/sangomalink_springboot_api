package com.kgoro.sangoma_link.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import java.util.Optional

interface TokenRepository: JpaRepository<Token, Long> {
    fun findByToken(token: String): Optional<Token>
    fun findUserDetailsByToken(token: String): UserDetails
}

