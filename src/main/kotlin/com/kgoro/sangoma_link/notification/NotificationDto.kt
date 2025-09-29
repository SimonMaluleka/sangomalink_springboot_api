package com.kgoro.sangoma_link.notification

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
    val userFullName: String
)