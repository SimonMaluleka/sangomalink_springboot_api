package com.kgoro.sangoma_link.notification

data class CreateNotificationRequest(
    val userId: Long,
    val title: String,
    val message: String,
    val notificationType: NotificationType? = null,
    val relatedEntityType: String? = null,
    val relatedEntityId: Long? = null,
    val actionUrl: String? = null,
    val metadata: Map<String, Any>? = null
)