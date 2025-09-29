package com.kgoro.sangoma_link.user

import com.kgoro.sangoma_link.sangoma_profile.SangomaProfile
import com.kgoro.sangoma_link.sangoma_profile.SangomaProfileRepository
import com.kgoro.sangoma_link.user.enums.ApprovalStatus
import com.kgoro.sangoma_link.user.enums.UserType
import com.sangomalink.domain.*
import jakarta.persistence.PostPersist
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserEntityListener(
    private val sangomaProfileRepository: SangomaProfileRepository
) {

    @PostPersist
    fun postPersist(user: User) {
        if (user.userType == UserType.Sangoma && user.sangomaProfile == null) {
            // Create profile with the full User object
            val profile = SangomaProfile(
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
                languages = listOf(),
                consultationApproach = null,
                isFeatured = false,
                profileApprovedAt = null,
                createdAt = LocalDateTime.now(),
                updatedAt = null
            )

            sangomaProfileRepository.save(profile)
        }
    }
}
