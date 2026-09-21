package com.kgoro.sangoma_link.sangoma.profile

import com.kgoro.sangoma_link.user.User
import com.kgoro.sangoma_link.user.enums.ApprovalStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime
import java.util.Optional

@Service
class ProfileService(
    private val profileRepository: SangomaProfileRepository
) {

    /**
     * Retrieves a single public profile by its primary database ID.
     */
    @Transactional(readOnly = true)
    fun getProfileById(id: Long): ProfileResponse {
        val profile = profileRepository.findByIdOrNull(id)
            ?: throw NoSuchElementException("Sangoma profile not found with ID: $id")

        return ProfileResponse.fromEntity(profile)
    }

    /**
     * Retrieves a public profile associated with a specific User ID.
     */
    @Transactional(readOnly = true)
    fun getProfileByUserId(userId: Long): ProfileResponse {
        val profile = profileRepository.findByUserId(userId)
            ?: throw NoSuchElementException("Sangoma profile not found for User ID: $userId")

        return ProfileResponse.fromEntity(profile)
    }

    /**
     * Fetches all public profiles that are currently marked as featured.
     */
    @Transactional(readOnly = true)
    fun getFeaturedProfiles(): List<ProfileResponse> {
        return profileRepository.findByIsFeaturedTrue()
            .map { ProfileResponse.fromEntity(it) }
    }

    fun createSangomaProfile(savedUser: User, sangomaSpecificData: SpecificData) {
        val profile = Profile(
            id = 0,
            user = savedUser,
            healingSpecialty = sangomaSpecificData.healingSpecialty,
            biography = sangomaSpecificData.biography,
            yearsOfExperience = sangomaSpecificData.yearsOfExperience,
            traditionalLineage = sangomaSpecificData.traditionalLineage,
            languagesSpoken = sangomaSpecificData.languagesSpoken,
            consultationApproach = "",
            averageRating = 0.0,
            totalReviews = 0,
            isFeatured = false,
            acceptsNewClients = true,
            profileApprovedAt = null,
            approvalStatus = ApprovalStatus.PENDING,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
        profileRepository.save<Profile>(profile)
    }

    fun findByUserId(id: Long): ProfileResponse {
        return profileRepository.findByUserId(id)
            ?.let { ProfileResponse.fromEntity(it) }
            ?: throw NoSuchElementException("Sangoma profile not found for User ID: $id")
    }
}
