package com.kgoro.sangoma_link.user.booking.event

import java.time.LocalDateTime

data class BookingCreatedEvent(
    val appointmentId: Long,
    val healerId: Long,
    val clientId: Long,
    val title: String,
    val actionUrl: String,
    val startTime: LocalDateTime,
    val relatedEntityType: String,
    val relatedEntityId: Long,
    val metadata: Map<String, Any>?
)
