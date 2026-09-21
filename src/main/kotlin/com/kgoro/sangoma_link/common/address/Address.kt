package com.kgoro.sangoma_link.common.address

import jakarta.persistence.*
import org.locationtech.jts.geom.Point
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime


@Entity
@Table(name = "user_addresses")
data class Address (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val province: String,
    val postalCode: String,
    val country: String,
    @Column(columnDefinition = "geography(Point, 4326)")
    var location: Point,
    val isPrimary: Boolean,
    val isDefault: Boolean,
    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
)