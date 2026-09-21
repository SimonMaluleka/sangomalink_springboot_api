package com.kgoro.sangoma_link.common.notification

import java.time.LocalDateTime

data class NotificationDto(
    val id: Long,
    val title: String,
    val message: String,
    val notificationType: NotificationType?,
    val isRead: Boolean,
    val relatedEntityType: String?,
    val relatedEntityId: Long?,
    val sentAt: LocalDateTime?,
    val readAt: LocalDateTime?,
    val createdAt: LocalDateTime,
    val actionUrl: String?,
    val metadata: Map<String, Any>?,
    val userId: Long,
   // val userFullName: String
){
    companion object {
        fun fromNotification(notification: Notification):NotificationDto{
            return NotificationDto(
                id = notification.id!!,
                title = notification.title,
                message = notification.message,
                notificationType = notification.  notificationType,
                isRead = notification.isRead,
                relatedEntityType = notification.relatedEntityType,
                relatedEntityId = notification.relatedEntityId,
                sentAt = notification. sentAt,
                readAt = notification.readAt,
                createdAt = notification.createdAt,
                actionUrl = notification.actionUrl,
                metadata = notification. metadata,
                userId = notification.userId,
                //userFullName = notification.userFullName
            )
        }
    }
}