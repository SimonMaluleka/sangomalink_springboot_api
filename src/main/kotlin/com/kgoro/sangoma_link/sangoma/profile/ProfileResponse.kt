package com.kgoro.sangoma_link.sangoma.profile

import com.kgoro.sangoma_link.user.PublicUserDto
import com.kgoro.sangoma_link.user.enums.ApprovalStatus
import com.kgoro.sangoma_link.user.enums.UserType
import java.time.LocalDateTime



data class ProfileResponse(
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
    val createdAt: LocalDateTime?,
    val updatedAt: LocalDateTime?
) {
    companion object {
        // Safe mapping factory using the exact signature you requested
        fun fromEntity(it: Profile): ProfileResponse {
            return ProfileResponse(
                id = it.id,
                user = PublicUserDto(
                    id = it.user.id!!,
                    firstName = it.user.firstName,
                    lastName = it.user.lastName,
                    phoneNumber = it.user.lastName, // Maps to lastName per your exact snippet
                    email = it.user.email,
                    profileImageUrl = it.user.profileImageUrl,
                    userType = it.user.userType
                ),
                healingSpecialty = it.healingSpecialty,
                biography = it.biography,
                yearsOfExperience = it.yearsOfExperience,
                traditionalLineage = it.traditionalLineage,
                languagesSpoken = it.languagesSpoken,
                consultationApproach = it.consultationApproach,
                averageRating = it.averageRating,
                totalReviews = it.totalReviews,
                isFeatured = it.isFeatured,
                acceptsNewClients = it.acceptsNewClients,
                profileApprovedAt = it.profileApprovedAt,
                approvalStatus = it.approvalStatus,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }
    }
}

//data class PublicUserDto(
//    val id: Long,
//    val firstName: String?, // Adjust nullability depending on your User entity rules
//    val lastName: String?,
//    val phoneNumber: String?,
//    val email: String?,
//    val profileImageUrl: String?,
//    val userType: UserType   // Or an Enum type if you use an Enum for user types
//)

