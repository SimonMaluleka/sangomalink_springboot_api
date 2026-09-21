package com.kgoro.sangoma_link.user.booking

import com.kgoro.sangoma_link.user.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
//@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/bookings")
class BookingController(
    val bookingService: BookingService,
    val userService: UserService
) {
//    @GetMapping
//    @PreAuthorize("isAuthenticated()")
//    fun getAllBookings(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<List<BookingResponse>> {
//        val user = userService.getUserByEmail(userDetails.username)
//
//        val bookings = bookingService.getAllBookings(user.id!!)
//
//        return ResponseEntity.ok(bookings)
//    }
//
//    @PostMapping
//    fun createBooking(@RequestBody request: BookingRequest) {
//        print("create booking endpoint")
//        val response = bookingService.createBooking(request)
//        print("\n $response")
//    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    fun bookings(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<List<BookingResponse>> {
        val user = userService.getUserByEmail(userDetails.username)

        val bookings = bookingService.getAllBookings(user.id!!)
        print("\n Booking $bookings")
        return ResponseEntity.ok(bookings)
    }

//    @GetMapping("/{bookingId}")
//    fun findById(@PathVariable bookingId: Long) = bookingService.findById(bookingId)

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{bookingId}/cancel")
    fun cancelBooking(
        @AuthenticationPrincipal userDetails: UserDetails,
        @PathVariable bookingId: Long,
        @RequestBody @Valid bookingCancellationRequest: BookingCancellationRequest
    ) = try {
        val user = userService.getUserByEmail(userDetails.username)
        print("cancel booking...")
        print(
            bookingService.cancelBooking(
                bookingId,
                user.id!!,
                //bookingCancellationRequest.cancellationReason
            )
        )
    } catch (e: Exception) {
        print(e.message)
    }


    @PutMapping("/{bookingId}/accept")
    fun acceptBooking(@PathVariable bookingId: Long, sangomaId: Long) = try {
        print("accepting booking...")
        val updated = bookingService.acceptBookingById(
            bookingId,
            sangomaId
        )

    } catch (e: Exception) {
        print(e.message)
    }

//    @PutMapping("/{bookingId}/decline")
//    fun declineBooking(@PathVariable bookingId: Long , sangomaId: Long): BookingResult = try {
//        print("accepting booking...")
//        val updated  = bookingService.declineBookingById(
//            bookingId,
//            sangomaId
//        ) as BookingResult
//    } catch (e: Exception){
//        print(e.message)
//    }
//    @PutMapping("/bookingId")
//    fun updateBooking(@PathVariable("bookingId") bookingId: Int, @RequestBody booking: BookingRequest) = bookingService.updateBookingById(
//        booking = booking,
//        id = bookingId
//    )
}