package com.kgoro.sangoma_link.shipping

import com.kgoro.sangoma_link.user.enums.ShippingStatus
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "shippings")
data class Shipping(
    @Id val id: Long,
    val orderId: Long,
    val trackingNumber: String? = null,
    val carrier: String? = null,
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val shippingStatus: ShippingStatus = ShippingStatus.PENDING,
    val shippedAt: LocalDateTime? = null,
    val estimatedDelivery: LocalDateTime? = null,
    val actualDelivery: LocalDateTime? = null
)