package com.kgoro.sangoma_link.user.booking

import com.kgoro.sangoma_link.user.enums.ReminderTiming
import com.kgoro.sangoma_link.user.enums.ReminderType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "booking_reminders")
data class BookingReminder(
    @Id
    val id: Long,

    @ManyToOne
    @JoinColumn(name = "booking_id", nullable = false)
    val booking: Booking,
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var reminderType: ReminderType,
    val sentAt: LocalDateTime? = null,
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var reminderTiming: ReminderTiming,
    val isDelivered: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
)