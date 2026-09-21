package com.kgoro.sangoma_link.sangoma.availability

//@Entity
//@Table(name ="sangoma_slots")
//data class Slot(
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    val id:  Long,
//    val sangomaId:  Long,
//    val startTime: OffsetDateTime,
//    val endTime: OffsetDateTime,
//    var isAvailable: Boolean = true,
//    val isRecurring: Boolean = false,
//    val recurrencePattern: String? = null,
//    @CreatedDate
//    val createdAt: OffsetDateTime = OffsetDateTime.now(),
//    @LastModifiedDate
//    val updatedAt: OffsetDateTime = OffsetDateTime.now(),
//    @Version
//    val version: Long = 0
//)

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.OffsetDateTime

@Entity
@Table(name = "sangoma_availability")
data class Availability(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "sangoma_id", nullable = false)
    val sangomaId: Long,

    @Column(name = "day_of_week", nullable = false)
    val dayOfWeek: Int, // 0..6

    @Column(nullable = false)
    var enabled: Boolean = true,

    @Column(nullable = false)
    var recurring: Boolean = true,

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb", nullable = false)
    var hours: List<Int> = emptyList(),

    @Column(name = "created_at", nullable = false)
    var createdAt: OffsetDateTime = OffsetDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    var updatedAt: OffsetDateTime = OffsetDateTime.now()
)
