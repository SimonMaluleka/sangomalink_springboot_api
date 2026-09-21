package com.kgoro.sangoma_link.user

import com.kgoro.sangoma_link.security.CurrentUser
import com.kgoro.sangoma_link.security.JwtService
import com.kgoro.sangoma_link.security.UserPrincipal
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val jwtService: JwtService
) {
    @PostMapping("/register")
    fun registerUser(@Valid @RequestBody request: CreateUserRequest): ResponseEntity<ResponseWrapper<AuthResponseDto>> {
        val user = userService.createUser(
            email = request.email,
            password = request.passwordHash,
            firstName = request.firstName,
            lastName = request.lastName,
            userType = request.userType,
            phoneNumber = request.phoneNumber,
            profileImageUrl = request.profileImageUrl,
            specificData = request.toSangomaSpecificData()
        )

//        val token = jwtService.generateToken(
//            user,
//
//            )

        val authResponse = AuthResponseDto(
            user = userMapper.toDto(user),
            accessToken = "generated_jwt_token_here",
            refreshToken = "generated_refresh_token_here",
            expiresIn = 3600L
        )

        val response = ResponseWrapper.success(
            data = authResponse,
            message = "User registered successfully"
        )

        return ResponseEntity
            .created(URI.create("/api/users/${user.id}"))
            .body(response)
    }

    @GetMapping("/public/{userId}")
    fun getUserPublicProfile(@PathVariable userId: Long): ResponseEntity<ResponseWrapper<PublicUserDto>> {
        val user = userService.getUserById(userId)
        val publicDto = userMapper.toPublicDto(user)

        val response = ResponseWrapper.success(
            data = publicDto,
            message = "User profile retrieved successfully"
        )

        return ResponseEntity.ok(response)
    }

    @GetMapping("/sangomas")
    fun getActiveSangomas(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(defaultValue = "firstName") sortBy: String,
        @RequestParam(defaultValue = "asc") direction: String,
        @RequestParam(required = false) search: String?
    ): ResponseEntity<ResponseWrapper<List<PublicUserDto>>> {

        val pageable = PageRequest.of(
            page, size,
            Sort.by(Sort.Direction.fromString(direction), sortBy)
        )

        val sangomasPage = if (search.isNullOrBlank()) {
            userService.getActiveSangomas()
        } else {
            userService.searchActiveSangomas(search)
        }

        val sangomaDtos = sangomasPage.map { userMapper.toPublicDto(it) }

        val response = ResponseWrapper.success(
            data = sangomaDtos,
            message = "Active sangomas retrieved successfully. Found ${sangomasPage.size} sangomas."
        )

        return ResponseEntity.ok(response)
    }

    // === AUTHENTICATED ENDPOINTS ===

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun getCurrentUser(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<ResponseWrapper<UserDto>> {
        print("\n Current user $userDetails")
        val user = userService.getUserByEmail(userDetails.username)

        print("\n $user")

        val userDto = userMapper.toDto(user)

        val response = ResponseWrapper.success(
            data = userDto,
            message = "User profile retrieved successfully"
        )

        return ResponseEntity.ok(response)
    }

    @PutMapping("/me/profile")
    @PreAuthorize("isAuthenticated()")
    fun updateCurrentUserProfile(
        @CurrentUser userPrincipal: UserPrincipal,
        @Valid @RequestBody request: UpdateUserProfileRequest
    ): ResponseEntity<ResponseWrapper<UserDto>> {
        val updatedUser = userService.updateUserProfile(
            userId = userPrincipal.id,
            firstName = request.firstName,
            lastName = request.lastName,
            phoneNumber = request.phoneNumber,
            profileImageUrl = request.profileImageUrl
        )

        val userDto = userMapper.toDto(updatedUser)

        val response = ResponseWrapper.success(
            data = userDto,
            message = "Profile updated successfully"
        )

        return ResponseEntity.ok(response)
    }

    @PutMapping("/me/password")
    @PreAuthorize("isAuthenticated()")
    fun changePassword(
        @CurrentUser userPrincipal: UserPrincipal,
        @Valid @RequestBody request: ChangePasswordRequest
    ): ResponseEntity<ResponseWrapper<Void>> {
        userService.changePassword(
            userId = userPrincipal.id,
            currentPassword = request.currentPassword,
            newPassword = request.newPassword
        )

        val response = ResponseWrapper.success<Void>(
            message = "Password changed successfully"
        )

        return ResponseEntity.ok(response)
    }

    @DeleteMapping("/me")
    @PreAuthorize("isAuthenticated()")
    fun deactivateCurrentUser(@CurrentUser userPrincipal: UserPrincipal): ResponseEntity<ResponseWrapper<Void>> {
        userService.deactivateUser(userPrincipal.id)

        val response = ResponseWrapper.success<Void>(
            message = "Account deactivated successfully"
        )

        return ResponseEntity.ok(response)
    }

    // === ADMIN ENDPOLongS ===

//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
//    fun getAllUsers(
//        @RequestParam(defaultValue = "0") page: Int,
//        @RequestParam(defaultValue = "20") size: Int,
//        @RequestParam(required = false) userType: UserType?,
//        @RequestParam(required = false) isActive: Boolean?,
//        @RequestParam(required = false) searchQuery: String?
//    ): ResponseEntity<ResponseWrapper<Page<UserDto>>> {
//        val users = userService.searchUsers(
//            query = searchQuery,
//            userType = userType,
//            isActive = isActive
//        )
//
//        // For simplicity - in real app implement pagination in service
//        val userDtos = users.map { userMapper.toDto(it) }
//        val pageable = PageRequest.of(page, size)
//        val pageResult = org.springframework.data.domain.PageImpl(userDtos, pageable, userDtos.size.toLong())
//
//        val response = ResponseWrapper.success(
//            data = pageResult,
//            message = "Users retrieved successfully"
//        )
//
//        return ResponseEntity.ok(response)
//    }
//
//    @GetMapping("/{userId}")
//    @PreAuthorize("hasRole('ADMIN')")
//    fun getUserById(@PathVariable userId: Long): ResponseEntity<ResponseWrapper<UserDto>> {
//        val user = userService.getUserById(userId)
//        val userDto = userMapper.toDto(user)
//
//        val response = ResponseWrapper.success(
//            data = userDto,
//            message = "User retrieved successfully"
//        )
//
//        return ResponseEntity.ok(response)
//    }

    @PutMapping("/{userId}/verify")
    @PreAuthorize("hasRole('ROLE_Admin')")
    fun verifyUser(@PathVariable userId: Long): ResponseEntity<ResponseWrapper<UserDto>> {
        val user = userService.verifyUser(userId)
        val userDto = userMapper.toDto(user)

        val response = ResponseWrapper.success(
            data = userDto,
            message = "User verified successfully"
        )

        return ResponseEntity.ok(response)
    }

//    @PutMapping("/{userId}/activate")
//    @PreAuthorize("hasRole('ADMIN')")
//    fun activateUser(@PathVariable userId: Long): ResponseEntity<ResponseWrapper<UserDto>> {
//        val user = userService.activateUser(userId)
//        val userDto = userMapper.toDto(user)
//
//        val response = ResponseWrapper.success(
//            data = userDto,
//            message = "User activated successfully"
//        )
//
//        return ResponseEntity.ok(response)
//    }

    @PutMapping("/{userId}/deactivate")
    @PreAuthorize("hasRole('ROLE_Admin')")
    fun deactivateUser(@PathVariable userId: Long): ResponseEntity<ResponseWrapper<UserDto>> {
        val user = userService.deactivateUser(userId)
        val userDto = userMapper.toDto(user)

        val response = ResponseWrapper.success(
            data = userDto,
            message = "User deactivated successfully"
        )

        return ResponseEntity.ok(response)
    }

//    @GetMapping("/{userId}/bookings")
//    @PreAuthorize("hasRole('ADMIN') or @securityService.isCurrentUser(#userId)")
//    fun getUserBookings(
//        @PathVariable userId: Long,
//        @RequestParam(defaultValue = "0") page: Long,
//        @RequestParam(defaultValue = "20") size: Long
//    ): ResponseEntity<ResponseWrapper<Page<BookingDto>>> {
//        // Implementation would delegate to BookingService
//        val response = ResponseWrapper.success<Page<BookingDto>>(
//            message = "User bookings retrieved successfully"
//        )
//        return ResponseEntity.ok(response)
//    }
//
//    @GetMapping("/{userId}/orders")
//    @PreAuthorize("hasRole('ADMIN') or @securityService.isCurrentUser(#userId)")
//    fun getUserOrders(
//        @PathVariable userId: Long,
//        @RequestParam(defaultValue = "0") page: Long,
//        @RequestParam(defaultValue = "20") size: Long
//    ): ResponseEntity<ResponseWrapper<Page<OrderDto>>> {
//        // Implementation would delegate to OrderService
//        val response = ResponseWrapper.success<Page<OrderDto>>(
//            message = "User orders retrieved successfully"
//        )
//        return ResponseEntity.ok(response)
//    }

    // === STATISTICS ENDPOLongS ===

    @GetMapping("/stats/counts")
    @PreAuthorize("hasRole('ADMIN')")
    fun getUserCounts(): ResponseEntity<ResponseWrapper<UserCountsDto>> {
        val totalUsers = userService.getTotalUserCount()
        val activeSangomas = userService.getActiveSangomaCount()
        val activeCustomers = userService.getActiveCustomerCount()
        val verifiedUsers = userService.getVerifiedUserCount()

        val counts = UserCountsDto(
            totalUsers = totalUsers,
            activeSangomas = activeSangomas,
            activeCustomers = activeCustomers,
            verifiedUsers = verifiedUsers
        )

        val response = ResponseWrapper.success(
            data = counts,
            message = "User statistics retrieved successfully"
        )

        return ResponseEntity.ok(response)
    }

//    @GetMapping("/stats/registration-trends")
//    @PreAuthorize("hasRole('ADMIN')")
//    fun getRegistrationTrends(
//        @RequestParam period: String = "30d" // 7d, 30d, 90d, 1y
//    ): ResponseEntity<ResponseWrapper<List<RegistrationTrendDto>>> {
//        val trends = userService.getRegistrationTrends(period)
//
//        val response = ResponseWrapper.success(
//            data = trends,
//            message = "Registration trends retrieved successfully"
//        )
//
//        return ResponseEntity.ok(response)
//    }
}