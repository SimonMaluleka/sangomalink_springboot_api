package com.kgoro.sangoma_link.user

import jakarta.validation.constraints.*
import com.fasterxml.jackson.annotation.JsonProperty
import com.kgoro.sangoma_link.sangoma_profile.SangomaSpecificData
import com.kgoro.sangoma_link.user.enums.UserType


data class CreateUserRequest(
    @field:NotBlank(message = "Email is required")
    @field:Email(message = "Valid email address is required")
    @field:JsonProperty("email")
    val email: String,

    @field:NotBlank(message = "Password is required")
    @field:Size(min = 8, message = "Password must be at least 8 characters")
    @field:JsonProperty("password")
    val passwordHash: String,

    @field:NotBlank(message = "First name is required")
    @field:Size(max = 100, message = "First name cannot exceed 100 characters")
    @field:JsonProperty("firstName")
    val firstName: String,

    @field:NotBlank(message = "Last name is required")
    @field:Size(max = 100, message = "Last name cannot exceed 100 characters")
    @field:JsonProperty("lastName")
    val lastName: String,

    @field:Pattern(
        regexp = "^\\+?[0-9\\-\\s()]{10,20}$",
        message = "Valid phone number is required"
    )
    @field:JsonProperty("phoneNumber")
    val phoneNumber: String? = null,

    @field:NotNull(message = "User type is required")
    @field:JsonProperty("userType")
    val userType: UserType = UserType.Customer,

    @field:JsonProperty("profileImageUrl")
    val profileImageUrl: String? = null,

    // Sangoma-specific fields (NB! These are optional for customers)
    @field:Size(max = 255, message = "Healing specialty cannot exceed 255 characters")
    @field:JsonProperty("healingSpecialty")
    val healingSpecialty: String? = null,

    @field:Size(max = 255, message = "Biography cannot exceed 255 characters")
    @field:JsonProperty("biography")
    val biography: String? = null,

    @field:Min(value = 0, message = "Years of experience cannot be negative")
    @field:Max(value = 100, message = "Years of experience cannot exceed 100")
    @field:JsonProperty("yearsOfExperience")
    val yearsOfExperience: Long? = null,

    @field:Size(max = 255, message = "Traditional lineage cannot exceed 255 characters")
    @field:JsonProperty("traditionalLineage")
    val traditionalLineage: String? = null,

    @field:Size(max = 500, message = "Languages spoken cannot exceed 500 characters")
    @field:JsonProperty("languagesSpoken")
    val languagesSpoken: List<String>? = null,


) {
    init {
        validateSangomaSpecificFields()
    }

    private fun validateSangomaSpecificFields() {
        if (userType == UserType.Sangoma) {
            require(!healingSpecialty.isNullOrBlank()) {
                "Healing specialty is required for sangoma registration"
            }
            require(yearsOfExperience != null && yearsOfExperience >= 0) {
                "Valid years of experience is required for sangoma registration"
            }
        }
    }

    // Helper function to clean/sanitize data
    fun sanitize(): CreateUserRequest = this.copy(
        email = email.trim().lowercase(),
        firstName = firstName.trim(),
        lastName = lastName.trim(),
        phoneNumber = phoneNumber?.trim(),
        healingSpecialty = healingSpecialty?.trim(),
        biography = biography?.trim(),
        traditionalLineage = traditionalLineage?.trim(),
        languagesSpoken = languagesSpoken  //?.trim(),
    )

    fun toSangomaSpecificData(): SangomaSpecificData? {
        return if (this.userType == UserType.Sangoma) {
            SangomaSpecificData(
                healingSpecialty = this.healingSpecialty ?: "",
                yearsOfExperience = this.yearsOfExperience ?: 0,
                traditionalLineage = this.traditionalLineage,
                languagesSpoken = this.languagesSpoken,
                biography = this.biography ?: "" // Can be updated later
            )
        } else {
            null
        }
    }

}