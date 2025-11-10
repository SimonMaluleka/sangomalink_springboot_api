package com.kgoro.sangoma_link.booking

import com.kgoro.sangoma_link.sangoma_profile.SangomaProfile
import com.kgoro.sangoma_link.service.SangomaService
import com.kgoro.sangoma_link.user.enums.BookingStatus
import com.kgoro.sangoma_link.user.enums.PaymentStatus
import jakarta.persistence.Column
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingRequest (
    val sub: String,
    val healerId: Long,
    val serviceName: String,
    var bookingStatus: BookingStatus = BookingStatus.PENDING,
    val scheduledFor: LocalDateTime,
    val scheduledUntil: LocalDateTime,
    val totalAmount: BigDecimal,
    val currency: String = "ZAR",
    var paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val customerNotes: String? = null,
    val sangomaNotes: String? = null,
    val meetingLink: String? = null,
    val location: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,
    val cancelledAt: LocalDateTime? = null,
    val cancellationReason: String? = null
)

//val sangoma: SangomaProfile,
//val service: SangomaService,
//var bookingStatus: BookingStatus = BookingStatus.PENDING,
//val scheduledFor: LocalDateTime,
//val scheduledUntil: LocalDateTime,
//val totalAmount: BigDecimal,
//val currency: String = "ZAR",
//var paymentStatus: PaymentStatus = PaymentStatus.PENDING,
//val customerNotes: String? = null,
//val sangomaNotes: String? = null,
//val meetingLink: String? = null,
//val location: String? = null,
//val createdAt: LocalDateTime = LocalDateTime.now(),
//val updatedAt: LocalDateTime = LocalDateTime.now(),
//val cancelledAt: LocalDateTime? = null,
//val cancellationReason: String? = null