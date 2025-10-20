package com.kgoro.sangoma_link.sangoma_profile

import com.kgoro.sangoma_link.user.PublicUserDto
import com.kgoro.sangoma_link.user.User
import org.springframework.stereotype.Component

@Component
class SangomaProfileMapper {
    fun toSangomaProfileResponse(sangomaProfile: SangomaProfile): SangomaProfileResponse{
        return SangomaProfileResponse(
            id = sangomaProfile.id,
            user = PublicUserDto(
                sangomaProfile.user.id!!,
                firstName = sangomaProfile.user.firstName,
                lastName = sangomaProfile.user.lastName,
                profileImageUrl = sangomaProfile.user.profileImageUrl,
                userType = sangomaProfile.user.userType,
            ),
            healingSpecialty = sangomaProfile.healingSpecialty,
            biography = sangomaProfile.biography,
            yearsOfExperience = sangomaProfile.yearsOfExperience,
            traditionalLineage = sangomaProfile.traditionalLineage,
            languagesSpoken = sangomaProfile.languagesSpoken,
            consultationApproach = sangomaProfile.consultationApproach,
            averageRating = sangomaProfile.averageRating,
            totalReviews = sangomaProfile.totalReviews,
            isFeatured = sangomaProfile.isFeatured,
            acceptsNewClients = sangomaProfile.acceptsNewClients,
            profileApprovedAt = sangomaProfile.profileApprovedAt,
            approvalStatus = sangomaProfile.approvalStatus,
            createdAt = sangomaProfile.createdAt,
            updatedAt = sangomaProfile.updatedAt
        )
    }
}