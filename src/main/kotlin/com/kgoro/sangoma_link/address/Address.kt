package com.kgoro.sangoma_link.address

import com.kgoro.sangoma_link.user.User
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "user_addresses")
data class Address (
    @Id
    @GeneratedValue
    val id: Int,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val province: String,
    val postalCode: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val isPrimary: Boolean,
    val isDefault: Boolean,
    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)