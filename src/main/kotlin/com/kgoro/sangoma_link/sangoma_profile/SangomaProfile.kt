package com.kgoro.sangoma_link.sangoma_profile

import com.kgoro.sangoma_link.user.User
import com.kgoro.sangoma_link.user.enums.ApprovalStatus
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDateTime

@Entity
class SangomaProfile(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    val user: User,
    val healingSpecialty: String?,
    val biography: String?,
    val yearsOfExperience: Long,
    val traditionalLineage: String?,
    val languages: List<String>?,
    val consultationApproach: String?,
    val averageRating: Double,
    val totalReviews: Long,
    val isFeatured: Boolean,
    val acceptsNewClients: Boolean,
    val profileApprovedAt: LocalDateTime?,
    val approvalStatus: ApprovalStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,

    )