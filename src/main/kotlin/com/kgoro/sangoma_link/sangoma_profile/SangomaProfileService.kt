package com.kgoro.sangoma_link.sangoma_profile

import com.kgoro.sangoma_link.user.User
import com.kgoro.sangoma_link.user.enums.ApprovalStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class SangomaProfileService (
    val sangomaProfileRepository: SangomaProfileRepository
){
    fun createSangomaProfile(savedUser: User, sangomaSpecificData: SangomaSpecificData) {
        val profile = SangomaProfile(
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
        sangomaProfileRepository.save<SangomaProfile>(profile)
    }

    fun findByIsFeaturedTrue(): List<SangomaProfile> {
        return sangomaProfileRepository.findByIsFeaturedTrue()
    }

    fun findAll(): List<SangomaProfile> {
        return sangomaProfileRepository.findAll();
    }
}