package com.kgoro.sangoma_link.user.booking.listener

import com.kgoro.sangoma_link.user.booking.event.BookingCreatedEvent
import com.kgoro.sangoma_link.common.notification.Notification
import com.kgoro.sangoma_link.common.notification.NotificationRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener

@Component
class BookingNotificationListener(
    private val notificationRepository: NotificationRepository
) {
    @Async
    @TransactionalEventListener
    fun handleBookingCreated(event: BookingCreatedEvent){
        val notification = Notification.createBookingNotification(
            userId = event.clientId,
            title = event.title,
            message = "Booking created for ${event.startTime}",
            bookingId = event.appointmentId,
            actionUrl = "/bookings/${event.appointmentId}",
            metadata = null
        )

        notificationRepository.save<Notification>(notification)
    }
}