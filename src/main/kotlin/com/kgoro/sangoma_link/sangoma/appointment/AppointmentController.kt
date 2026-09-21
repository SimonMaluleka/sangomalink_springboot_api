package com.kgoro.sangoma_link.sangoma.appointment

import com.kgoro.sangoma_link.sangoma.profile.ProfileService
import com.kgoro.sangoma_link.user.UserService
import com.kgoro.sangoma_link.user.booking.BookingResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/appointments")
class AppointmentController (
    val appointmentService: AppointmentService,
    val userService: UserService,
    val profileService: ProfileService
){
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    fun appointments(
        @AuthenticationPrincipal userDetails: UserDetails,
    ): ResponseEntity<List<BookingResponse>>{
        val user = userService.getUserByEmail(userDetails.username)
        val profile = profileService.findByUserId(user.id!!)
        print("\n ${user.userType} \n ${profile.id}\n")
        return ResponseEntity.ok(appointmentService.findUpcomingBookingBySangomaId(profile.id))
    }
}