package com.kgoro.sangoma_link.user

import com.kgoro.sangoma_link.sangoma.profile.Profile
import org.springframework.stereotype.Component

@Component
class UserMapper {

    fun toDto(user: User, includeSensitiveData: Boolean = true): UserDto {
        return UserDto(
            id = user.id,
            email = if (includeSensitiveData) user.email else "",
            firstName = user.firstName,
            lastName = user.lastName,
            phoneNumber = if (includeSensitiveData) user.phoneNumber else null,
            userType = user.userType,
            profileImageUrl = user.profileImageUrl,
            isVerified = user.isVerified,
            isActive = user.isActive,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt,
            sangomaProfile = user.profile?.toDto()
        )
    }

    fun toPublicDto(user: User): PublicUserDto {
        return PublicUserDto(
            id = user.id!!,
            firstName = user.firstName,
            lastName = user.lastName,
            email = user.email,
            phoneNumber = user.phoneNumber!!,
            profileImageUrl = user.profileImageUrl,
            userType = user.userType
        )
    }

    fun toEntity(request: CreateUserRequest): User {
        return User(
            email = request.email,
            passwordHash = "", // Will be set separately after encoding
            firstName = request.firstName,
            lastName = request.lastName,
            phoneNumber = request.phoneNumber,
            userType = request.userType,
            profileImageUrl = request.profileImageUrl,
            isVerified = false,
            isActive = true
        )
    }
}

// Extension function for SangomaProfile
fun Profile.toDto(): SangomaProfileDto {
    return SangomaProfileDto(
        id = this.id,
        healingSpecialty = this.healingSpecialty,
        biography = this.biography,
        yearsOfExperience = this.yearsOfExperience,
        traditionalLineage = this.traditionalLineage,
        languages = this.languagesSpoken,
        consultationApproach = this.consultationApproach,
        averageRating = this.averageRating,
        totalReviews = this.totalReviews,
        isFeatured = this.isFeatured,
        acceptsNewClients = this.acceptsNewClients,
        approvalStatus = this.approvalStatus,
        profileApprovedAt = this.profileApprovedAt
    )
}