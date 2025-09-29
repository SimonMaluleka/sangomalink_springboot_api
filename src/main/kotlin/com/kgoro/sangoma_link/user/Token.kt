package com.kgoro.sangoma_link.user

import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
class Token(
    @Id
    @GeneratedValue
    val id: Long = 0,
    val token: String,
    val createdAt: LocalDateTime,
    val expiresAt: LocalDateTime,
    var validated: LocalDateTime,
    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    val user: User
) {
    class Builder {
        private lateinit var token: String
        private lateinit var createdAt: LocalDateTime
        private lateinit var expiresAt: LocalDateTime
        private lateinit var validated : LocalDateTime
        private lateinit var user: User


        fun setToken(token: String)= apply { this.token = token }
        fun setCreatedAt(createdAt: LocalDateTime)= apply { this.createdAt = createdAt }
        fun setExpiresAt(expiresAt: LocalDateTime)= apply { this.expiresAt = expiresAt }
        fun setValidated(validated: LocalDateTime) = apply { this.validated = validated }
        fun setUser(user: User) = apply { this.user = user }

        fun build(): Token {
            return Token(
                id = 0,
                token,
                createdAt,
                expiresAt,
                validated,
                user
            )
        }
    }
}