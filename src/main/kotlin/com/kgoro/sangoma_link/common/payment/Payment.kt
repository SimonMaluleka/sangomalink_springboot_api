package com.kgoro.sangoma_link.common.payment

import com.kgoro.sangoma_link.user.enums.PaymentMethod
import com.kgoro.sangoma_link.user.enums.PaymentStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "payments")
data class Payment(
    @Id val id: Long,
    val orderId: Long? = null,
    val bookingId: Long? = null,
    @Column(nullable = false, precision = 10, scale = 2)
    val amount: BigDecimal,
    @Column(length = 3)
    val currency: String = "ZAR",
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    val paymentMethod: PaymentMethod,
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val paymentGatewayReference: String? = null,
    val paymentDate: LocalDateTime? = null,
    val paymentNotes: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)
