package com.kgoro.sangoma_link.notification


import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface NotificationRepository : JpaRepository<Notification, Long> {

    fun findByUserIdOrderByCreatedAtDesc(userId: Long): List<Notification>

    fun findByUserIdAndIsReadOrderByCreatedAtDesc(userId: Long, isRead: Boolean): List<Notification>

    fun findByUserIdAndNotificationTypeOrderByCreatedAtDesc(
        userId: Long,
        notificationType: NotificationType
    ): List<Notification>

    fun findByRelatedEntityTypeAndRelatedEntityId(
        relatedEntityType: String,
        relatedEntityId: Long
    ): List<Notification>

    fun countByUserIdAndIsRead(userId: Long, isRead: Boolean): Long

    fun deleteByUserIdAndIsReadAndCreatedAtBefore(
        userId: Long,
        isRead: Boolean,
        cutoffDate: LocalDateTime
    ): Long

    @Query("SELECT n FROM Notification n WHERE n.userId = :userId AND n.createdAt > :after")
    fun findRecentByUserId(
        @Param("userId") userId: Long,
        @Param("after") after: LocalDateTime
    ): List<Notification>


    @Query("""
    SELECT n FROM Notification n 
    WHERE n.userId = :userId 
    AND n.isRead = false 
    AND n.sentAt IS NOT NULL
""")
    fun findUnreadSentNotifications(@Param("userId") userId: Long): List<Notification>

}