package com.kgoro.sangoma_link.notification

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDateTime

@Entity
@Table(
    name = "notifications",
    indexes = [
        Index(name = "idx_notifications_user_id", columnList = "user_id"),
        Index(name = "idx_notifications_is_read", columnList = "is_read"),
        Index(name = "idx_notifications_sent_at", columnList = "sent_at"),
        Index(name = "idx_notifications_related_entity", columnList = "related_entity_type, related_entity_id")
    ]
)
data class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "user_id", nullable = false)
    val userId: Long,

    @Column(nullable = false, length = 255)
    val title: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    val message: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", length = 20)
    val notificationType: NotificationType? = null,

    @Column(name = "is_read", nullable = false)
    val isRead: Boolean = false,

    @Column(name = "related_entity_type", length = 50)
    val relatedEntityType: String? = null,

    @Column(name = "related_entity_id")
    val relatedEntityId: Long? = null,

    @Column(name = "sent_at")
    val sentAt: LocalDateTime? = null,

    @Column(name = "read_at")
    val readAt: LocalDateTime? = null,

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "action_url", length = 500)
    val actionUrl: String? = null,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    val metadata: Map<String, Any>? = null
) {
    // Business logic methods
    fun markAsRead(): Notification =
        if (!isRead) this.copy(isRead = true, readAt = LocalDateTime.now()) else this

    fun markAsUnread(): Notification =
        if (isRead) this.copy(isRead = false, readAt = null) else this

    fun isRelatedTo(entityType: String, entityId: Long): Boolean =
        relatedEntityType == entityType && relatedEntityId == entityId

    fun isOfType(type: NotificationType): Boolean =
        notificationType == type

    fun isForUser(userId: Long): Boolean =
        this.userId == userId

    fun getAgeInMinutes(): Long =
        java.time.Duration.between(createdAt, LocalDateTime.now()).toMinutes()

    fun isRecent(minutes: Long = 60): Boolean =
        getAgeInMinutes() <= minutes

    companion object {
        fun createBookingNotification(
            userId: Long,
            title: String,
            message: String,
            bookingId: Long,
            actionUrl: String? = null
        ): Notification =
            Notification(
                userId = userId,
                title = title,
                message = message,
                notificationType = NotificationType.BOOKING,
                relatedEntityType = "booking",
                relatedEntityId = bookingId,
                sentAt = LocalDateTime.now(),
                actionUrl = actionUrl
            )

        fun createOrderNotification(
            userId: Long,
            title: String,
            message: String,
            orderId: Long,
            actionUrl: String? = null
        ): Notification =
            Notification(
                userId = userId,
                title = title,
                message = message,
                notificationType = NotificationType.ORDER,
                relatedEntityType = "order",
                relatedEntityId = orderId,
                sentAt = LocalDateTime.now(),
                actionUrl = actionUrl
            )

        fun createSystemNotification(
            userId: Long,
            title: String,
            message: String,
            actionUrl: String? = null,
            metadata: Map<String, Any>? = null
        ): Notification =
            Notification(
                userId = userId,
                title = title,
                message = message,
                notificationType = NotificationType.SYSTEM,
                sentAt = LocalDateTime.now(),
                actionUrl = actionUrl,
                metadata = metadata
            )

        fun createPromotionalNotification(
            userId: Long,
            title: String,
            message: String,
            actionUrl: String? = null,
            metadata: Map<String, Any>? = null
        ): Notification =
            Notification(
                userId = userId,
                title = title,
                message = message,
                notificationType = NotificationType.PROMOTIONAL,
                sentAt = LocalDateTime.now(),
                actionUrl = actionUrl,
                metadata = metadata
            )
    }

    override fun toString(): String {
        return "Notification(id=$id, title='$title', type=$notificationType, isRead=$isRead, userId=$userId)"
    }
}
