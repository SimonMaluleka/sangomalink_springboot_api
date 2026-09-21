package com.kgoro.sangoma_link.user

import com.kgoro.sangoma_link.sangoma.profile.Profile
import com.kgoro.sangoma_link.sangoma.profile.SangomaProfileRepository
import com.kgoro.sangoma_link.user.enums.ApprovalStatus
import com.kgoro.sangoma_link.user.enums.UserType
import jakarta.persistence.PostPersist
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserEntityListener(
    private val profileRepository: SangomaProfileRepository
) {

    @PostPersist
    fun postPersist(user: User) {
        if (user.userType == UserType.Sangoma && user.profile == null) {
            // Create profile with the full User object
            val profile = Profile(
                id = 0, // Let JPA auto-generate it
                user = user, // Now passing the full User object
                approvalStatus = ApprovalStatus.PENDING,
                acceptsNewClients = true,
                totalReviews = 0,
                averageRating = 0.0,
                healingSpecialty = null,
                biography = null,
                yearsOfExperience = 0,
                traditionalLineage = null,
                languagesSpoken = listOf(),
                consultationApproach = null,
                isFeatured = false,
                profileApprovedAt = null,
                createdAt = LocalDateTime.now(),
                updatedAt = null
            )

            profileRepository.save(profile)
        }
    }
}
