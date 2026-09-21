package com.kgoro.sangoma_link.admin

import com.kgoro.sangoma_link.sangoma.profile.ProfileResponse
import com.kgoro.sangoma_link.user.PublicUserDto
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin")
class AdminController(
    val adminService: AdminService
) {
    @GetMapping("/sangomas/pending")
    fun getPendingProfiles(
        @RequestParam status: Int
    ): ResponseEntity<List<ProfileResponse>> {
        val profiles = adminService.getPendingProfiles(status).map {
            ProfileResponse(
                id = it.id,
                user = PublicUserDto(
                    id = it.user.id!!,
                    firstName = it.user.firstName,
                    lastName = it.user.lastName,
                    phoneNumber = it.user.lastName,
                    email = it.user.email,
                    profileImageUrl = it.user.profileImageUrl,
                    userType = it.user.userType
                ),
                healingSpecialty = it.healingSpecialty,
                biography = it.biography,
                yearsOfExperience = it.yearsOfExperience,
                traditionalLineage = it.traditionalLineage,
                languagesSpoken = it.languagesSpoken,
                consultationApproach = it.consultationApproach,
                averageRating = it.averageRating,
                totalReviews = it.totalReviews,
                isFeatured = it.isFeatured,
                acceptsNewClients = it.acceptsNewClients,
                profileApprovedAt = it.profileApprovedAt,
                approvalStatus = it.approvalStatus,
                createdAt = it.createdAt,
                updatedAt = it.updatedAt
            )
        }.toList()

        return ResponseEntity.ok(profiles)
    }
    @PutMapping("/sangomas/{id}/approve")
    fun approveProfile(@PathVariable id: Long): ResponseEntity<Int>{
        return ResponseEntity.ok(adminService.approveSangoma(id))
    }

    @PutMapping("/sangomas/{id}/reject")
    fun rejectProfile(@PathVariable id: Long): ResponseEntity<Int>{
        return ResponseEntity.ok(adminService.rejectSangoma(id))
    }
}