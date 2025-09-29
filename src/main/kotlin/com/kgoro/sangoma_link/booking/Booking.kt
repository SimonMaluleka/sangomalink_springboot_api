package com.kgoro.sangoma_link.booking


import com.kgoro.sangoma_link.sangoma_profile.SangomaProfile
import com.kgoro.sangoma_link.service.SangomaService
import com.kgoro.sangoma_link.user.User
import com.kgoro.sangoma_link.user.enums.BookingStatus
import com.kgoro.sangoma_link.user.enums.PaymentStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "bookings")
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @ManyToOne
    @JoinColumn(name = "sangoma_id", nullable = false)
    val sangoma: SangomaProfile,
    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    val service: SangomaService,
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var bookingStatus: BookingStatus = BookingStatus.PENDING,
    @Column(nullable = false)
    val scheduledFor: LocalDateTime,
    @Column(nullable = false)
    val scheduledUntil: LocalDateTime,
    @Column(nullable = false, precision = 10, scale = 2)
    val totalAmount: BigDecimal,
    @Column(length = 3)
    val currency: String = "ZAR",
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val customerNotes: String? = null,
    val sangomaNotes: String? = null,
    val meetingLink: String? = null,
    val location: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val cancelledAt: LocalDateTime? = null,
    val cancellationReason: String? = null
)