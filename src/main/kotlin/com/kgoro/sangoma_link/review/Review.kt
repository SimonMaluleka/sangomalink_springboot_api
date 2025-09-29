package com.kgoro.sangoma_link.review

import com.kgoro.sangoma_link.booking.Booking
import com.kgoro.sangoma_link.sangoma_profile.SangomaProfile
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reviews")
data class Review(
    @Id
    val id: Long,
    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false, unique = true)
    val booking: Booking,
    val customerId: Long,
    @ManyToOne
    @JoinColumn(name = "sangoma_id", nullable = false)
    val sangoma: SangomaProfile,
    @Column(nullable = false)
    val rating: Long,
    val comment: String? = null,
    val sangomaResponse: String? = null,
    val respondedAt: LocalDateTime? = null,
    val isVerifiedBooking: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)