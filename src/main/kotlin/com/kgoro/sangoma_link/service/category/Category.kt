package com.kgoro.sangoma_link.service.category

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "service_categories")
data class Category(
    @Id
    val id: Long,
    @Column(nullable = false)
    val name: String,
    val description: String? = null,
    val displayOrder: Long = 0,
    val isActive: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)