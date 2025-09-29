package com.kgoro.sangoma_link.user
import com.kgoro.sangoma_link.user.enums.UserType
import java.time.LocalDateTime

class UserResponse(
    val id: Long?,
    val email: String,
    val firstname: String,
    val lastname: String,
    val fullname: String,
    val phoneNumber: String?,
    val userType: UserType,
    val profileImageUrl: String?,
    var isVerified: Boolean,
    var isActive: Boolean,
    val lastLoginAt: LocalDateTime?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
)
