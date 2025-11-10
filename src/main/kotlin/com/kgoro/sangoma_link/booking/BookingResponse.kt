package com.kgoro.sangoma_link.booking

import com.kgoro.sangoma_link.user.enums.BookingStatus
import com.kgoro.sangoma_link.user.enums.PaymentStatus
import com.kgoro.sangoma_link.user.enums.UserType
import java.math.BigDecimal
import java.time.LocalDateTime

class BookingResponse(
    val bookingId: Long,
    val sangomaName: String,
    val sangomaProfileImage: String?,
    val sangomaPhoneNumber: String?,
    val serviceName: String,
    val scheduledDateTime: LocalDateTime,
    val duration: Long,
    val totalAmount: BigDecimal,
    val bookingStatus: BookingStatus,
    val location: String?,
    val customerNotes: String?,
    val sangomaNotes: String?,
    val paymentStatus: PaymentStatus,
    val cancellationReason: String?,
    val userType: UserType
)