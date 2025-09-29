package com.kgoro.sangoma_link.user

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.kgoro.sangoma_link.user.enums.ApprovalStatus
import com.kgoro.sangoma_link.user.enums.UserType
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class UserDto(
    @field:JsonProperty("id")
    val id: Long?,

    @field:JsonProperty("email")
    val email: String,

    @field:JsonProperty("firstName")
    val firstName: String,

    @field:JsonProperty("lastName")
    val lastName: String,

    @field:JsonProperty("phoneNumber")
    val phoneNumber: String?,

    @field:JsonProperty("userType")
    val userType: UserType,

    @field:JsonProperty("profileImageUrl")
    val profileImageUrl: String?,

    @field:JsonProperty("isVerified")
    val isVerified: Boolean,

    @field:JsonProperty("isActive")
    val isActive: Boolean,

    @field:JsonProperty("createdAt")
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val createdAt: LocalDateTime,

    @field:JsonProperty("updatedAt")
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val updatedAt: LocalDateTime,

    // Sangoma profile data (only included for sangoma users)
    @field:JsonProperty("sangomaProfile")
    val sangomaProfile: SangomaProfileDto? = null
)

// Simplified DTO for public listing (without sensitive data)
data class PublicUserDto(
    @field:JsonProperty("id")
    val id: Long,

    @field:JsonProperty("firstName")
    val firstName: String,

    @field:JsonProperty("lastName")
    val lastName: String,

    @field:JsonProperty("profileImageUrl")
    val profileImageUrl: String?,

    @field:JsonProperty("userType")
    val userType: UserType
)

// DTO for authentication response
data class AuthResponseDto(
    @field:JsonProperty("user")
    val user: UserDto,

    @field:JsonProperty("accessToken")
    val accessToken: String,

    @field:JsonProperty("refreshToken")
    val refreshToken: String,

    @field:JsonProperty("tokenType")
    val tokenType: String = "Bearer",

    @field:JsonProperty("expiresIn")
    val expiresIn: Long
)

// DTO for user profile updates
data class UpdateUserProfileRequest(
    @field:Size(max = 100, message = "First name cannot exceed 100 characters")
    @field:JsonProperty("firstName")
    val firstName: String? = null,

    @field:Size(max = 100, message = "Last name cannot exceed 100 characters")
    @field:JsonProperty("lastName")
    val lastName: String? = null,

    @field:Pattern(
        regexp = "^\\+?[0-9\\-\\s()]{10,20}$",
        message = "Valid phone number is required"
    )
    @field:JsonProperty("phoneNumber")
    val phoneNumber: String? = null,

    @field:JsonProperty("profileImageUrl")
    val profileImageUrl: String? = null
)

// Sangoma Profile DTO
data class SangomaProfileDto(
    @field:JsonProperty("id")
    val id: Long,

    @field:JsonProperty("healingSpecialty")
    val healingSpecialty: String?,

    @field:JsonProperty("biography")
    val biography: String?,

    @field:JsonProperty("yearsOfExperience")
    val yearsOfExperience: Long,

    @field:JsonProperty("traditionalLineage")
    val traditionalLineage: String?,

    @field:JsonProperty("languagesSpoken")
    val languages: List<String>?,

    @field:JsonProperty("consultationApproach")
    val consultationApproach: String?,

    @field:JsonProperty("averageRating")
    val averageRating: Double,

    @field:JsonProperty("totalReviews")
    val totalReviews: Long,

    @field:JsonProperty("isFeatured")
    val isFeatured: Boolean,

    @field:JsonProperty("acceptsNewClients")
    val acceptsNewClients: Boolean,

    @field:JsonProperty("approvalStatus")
    val approvalStatus: ApprovalStatus,

    @field:JsonProperty("profileApprovedAt")
    @field:JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    val profileApprovedAt: LocalDateTime?
)

data class ChangePasswordRequest(
    @field:NotBlank(message = "Current password is required")
    val currentPassword: String,

    @field:NotBlank(message = "New password is required")
    @field:Size(min = 8, message = "New password must be at least 8 characters")
    val newPassword: String
)

data class UserCountsDto(
    val totalUsers: Long,
    val activeSangomas: Long,
    val activeCustomers: Long,
    val verifiedUsers: Long
)

data class RegistrationTrendDto(
    val date: String,
    val registrations: Long,
    val userType: UserType
)

// Extension function for CreateUserRequest
fun CreateUserRequest.toSangomaSpecificData(): SangomaSpecificData? {
    return if (this.userType == UserType.Sangoma) {
        SangomaSpecificData(
            healingSpecialty = this.healingSpecialty ?: "",
            yearsOfExperience = this.yearsOfExperience ?: 0,
            traditionalLineage = this.traditionalLineage,
            languagesSpoken = this.languagesSpoken,
            biography = null // Can be updated later
        )
    } else {
        null
    }
}
