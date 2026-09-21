package com.kgoro.sangoma_link.user

import com.kgoro.sangoma_link.exception.EmailAlreadyExistsException
import com.kgoro.sangoma_link.exception.UserNotFoundException
import com.kgoro.sangoma_link.sangoma.profile.ProfileService
import com.kgoro.sangoma_link.sangoma.profile.SpecificData
import com.kgoro.sangoma_link.user.enums.UserType

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val profileService: ProfileService
) {

    fun createUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        userType: UserType,
        phoneNumber: String? = null,
        profileImageUrl: String? = null,
        specificData: SpecificData? = null
    ): User {
        // Validate email uniqueness
        if (userRepository.existsByEmail(email)) {
            throw EmailAlreadyExistsException("Email already registered: $email")
        }

        // Create user entity
        val user = when (userType) {
            UserType.Sangoma -> User.createSangoma(
                email = email,
                passwordHash = passwordEncoder.encode(password),
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                profileImageUrl = profileImageUrl
            )
            UserType.Customer -> User.createCustomer(
                email = email,
                passwordHash = passwordEncoder.encode(password),
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                profileImageUrl = profileImageUrl
            )

            else -> {
                User.createAdmin(
                    email = email,
                    passwordHash = passwordEncoder.encode(password),
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber,
                    profileImageUrl = profileImageUrl
                )
            }
        }

        val savedUser = userRepository.save(user)

        // Create sangoma profile if user type is sangoma
        if (userType == UserType.Sangoma && specificData != null) {
            profileService.createSangomaProfile(savedUser, specificData)
        }

        return savedUser
    }

    fun getUserById(id: Long): User {
        return userRepository.findByIdAndIsActiveTrue(id)
            .orElseThrow { UserNotFoundException("User not found with id: $id") }
    }

    fun getUserByEmail(email: String): User {
        return userRepository.findActiveUserByEmail(email)
            ?: throw UserNotFoundException("User not found with email: $email")
    }

    fun updateUserProfile(
        userId: Long,
        firstName: String? = null,
        lastName: String? = null,
        phoneNumber: String? = null,
        profileImageUrl: String? = null
    ): User {
        val user = getUserById(userId)
        val updatedUser = user.updateProfile(
            firstName = firstName,
            lastName = lastName,
            phoneNumber = phoneNumber,
            profileImageUrl = profileImageUrl
        )
        return userRepository.save(updatedUser)
    }

    fun deactivateUser(userId: Long): User {
        val user = getUserById(userId)
        val deactivatedUser = user.deactivate()
        return userRepository.save(deactivatedUser)
    }

    fun verifyUser(userId: Long): User {
        val user = getUserById(userId)
        val verifiedUser = user.markAsVerified()
        return userRepository.save(verifiedUser)
    }

    fun updateLastLogin(userId: Long): User {
        val user = getUserById(userId)
        val updatedUser = user.updateLastLogin()
        return userRepository.save(updatedUser)
    }

    fun searchUsers(
        query: String? = null,
        userType: UserType? = null,
        isActive: Boolean? = true
    ): List<User> {
        // Implementation for searching users
        return emptyList() // Replace with actual implementation
    }

    fun getActiveSangomas(): List<User> {
        return userRepository.findByUserTypeAndIsActiveTrue(
            UserType.Sangoma
        )
    }

    fun searchActiveSangomas(
        searchQuery: String? = null,

    ): List<User> {
        return if (searchQuery.isNullOrBlank()) {
            userRepository.findByUserTypeAndIsActiveTrue(UserType.Sangoma)
        } else {
            userRepository.findActiveSangomasBySearchQuery(searchQuery)
        }
    }
//    fun getApprovedSangomas(): List<User> {
//        return userRepository.findApprovedSangomas()
//    }

    fun changePassword(userId: Long, currentPassword: String, newPassword: String) {}
    fun activateUser(userId: Long) {}
    fun getTotalUserCount(): Long {
        return userRepository.count()
    }

    fun getActiveSangomaCount(): Long {
        return userRepository.countActiveUsersByType(UserType.Sangoma)
    }

    fun getActiveCustomerCount() : Long{
        return userRepository.countActiveUsersByType(UserType.Customer)
    }

    fun getVerifiedUserCount(): Long {
        return userRepository.findByIsVerifiedTrue().size.toLong()
    }

    fun getRegistrationTrends(period: String) {
        TODO("Not yet implemented")
    }

    fun saveUserProfileImageMetadata(finalFilename: String, userId: Long) {
        userRepository.saveUserProfileImageMetadata(finalFilename, userId)
    }
}

