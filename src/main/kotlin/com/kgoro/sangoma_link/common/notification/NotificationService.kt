package com.kgoro.sangoma_link.common.notification

import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class NotificationService(
    private val notificationRepository: NotificationRepository
) {
    fun createNotification(request: CreateNotificationRequest): NotificationDto {
        println("\n\nCreating a new notification for booking: ${request.title}")
        val entity = Notification(
            userId = request.userId,
            title = request.title,
            message = request.message,
            notificationType = request.notificationType,
            relatedEntityType = request.relatedEntityType,
            relatedEntityId = request.relatedEntityId,
            actionUrl = request.actionUrl,
            metadata = request.metadata,
            sentAt = LocalDateTime.now()
        )

        val savedNotification = notificationRepository.save(entity)

        return NotificationDto.fromNotification(savedNotification)
    }
}