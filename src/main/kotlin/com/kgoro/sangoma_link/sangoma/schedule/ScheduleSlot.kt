package com.kgoro.sangoma_link.sangoma.schedule

import com.kgoro.sangoma_link.sangoma.profile.Profile
import jakarta.persistence.*
import java.time.LocalDateTime


@Entity
@Table(name = "sangoma_slots")
data class ScheduleSlot(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sangoma_id", nullable = false)
    val sangoma: Profile,
    @Column(nullable = false)
    val startTime: LocalDateTime,
    @Column(nullable = false)
    val endTime: LocalDateTime,
    @Column(nullable = false)
    val isAvailable: Boolean,
    @Column(nullable = false)
    val isRecurring: Boolean,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val recurrencePattern: Reccurence = Reccurence.WEEKLY,
    @Column(name = "created_at",  updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @Column(name = "updated_at", insertable = false, updatable = false)
    var updatedAt: LocalDateTime? = null,
    @Column(nullable = false)
    val version: Int

//    val status: SlotStatus = SlotStatus.AVAILABLE
)

enum class Reccurence{
    WEEKLY,
    MONTHLY,
    YEARLY
}

