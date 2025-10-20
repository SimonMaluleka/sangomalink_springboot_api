package com.kgoro.sangoma_link.sangoma_profile

import com.kgoro.sangoma_link.user.PublicUserDto
import com.kgoro.sangoma_link.user.User
import com.kgoro.sangoma_link.user.enums.ApprovalStatus
import jakarta.persistence.Column
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDateTime

class SangomaProfileResponse(
    val id: Long,
    val user: PublicUserDto,
    val healingSpecialty: String?,
    val biography: String?,
    val yearsOfExperience: Long,
    val traditionalLineage: String?,
    val languagesSpoken: List<String>?,
    val consultationApproach: String?,
    val averageRating: Double,
    val totalReviews: Long,
    val isFeatured: Boolean,
    val acceptsNewClients: Boolean,
    val profileApprovedAt: LocalDateTime?,
    val approvalStatus: ApprovalStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
) {
}