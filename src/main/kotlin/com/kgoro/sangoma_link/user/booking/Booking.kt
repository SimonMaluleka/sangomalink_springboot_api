package com.kgoro.sangoma_link.user.booking


import com.kgoro.sangoma_link.sangoma.profile.Profile
import com.kgoro.sangoma_link.sangoma.availability.Availability
import com.kgoro.sangoma_link.sangoma.practice.Practice
import com.kgoro.sangoma_link.user.enums.BookingStatus
import com.kgoro.sangoma_link.user.enums.PaymentStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "bookings")
data class Booking(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "user_id", nullable = false)
    val userId: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sangoma_id", nullable = false)
    val sangoma: Profile,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    val practice: Practice,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "availability_id", nullable = false)
    val availability: Availability,
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var bookingStatus: BookingStatus = BookingStatus.PENDING,
    @Column(name = "scheduled_for", nullable = false)
    val scheduledFor: LocalDateTime,
    @Column(name = "scheduled_until", nullable = false)
    val scheduledUntil: LocalDateTime,
    @Column(name = "total_amount", precision = 10, scale = 2, nullable = false)
    val totalAmount: BigDecimal,
    @Column(name = "currency", length = 3, nullable = false)
    val currency: String = "ZAR",
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", length = 20, nullable = false)
    var paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    @Column(name = "customer_notes", length = 255)
    var customerNotes: String? = null,
    @Column(name = "sangoma_notes", length = 255)
    var sangomaNotes: String? = null,
    @Column(name = "meeting_link", length = 255)
    var meetingLink: String? = null,
    @Column(name = "location", length = 255)
    var location: String? = null,
    @Column(name = "created_at",  updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", insertable = false, updatable = false)
    var updatedAt: LocalDateTime? = null,
    @Column(name = "cancelled_at")
    val cancelledAt: LocalDateTime? = null,
    @Column(name = "cancellation_reason", length = 255)
    val cancellationReason: String? = null,

    )


