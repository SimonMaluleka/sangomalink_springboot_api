package com.kgoro.sangoma_link.booking

import com.kgoro.sangoma_link.user.UserService
import jakarta.validation.Valid
import org.springframework.data.jpa.repository.Query
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bookings")
class BookingController(
    val bookingService: BookingService,
    val userService: UserService
){
//    @GetMapping
//    fun getAllBookings(): ResponseEntity<List<Booking>> = ResponseEntity.ok(bookingService.getAllBookings())

    @PostMapping
    fun createBooking(@RequestBody booking: BookingRequest) {
        bookingService.createBooking(booking)
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    fun getAllBookings(@AuthenticationPrincipal userDetails: UserDetails) : ResponseEntity<List<BookingResponse>> {
        val user = userService.getUserByEmail(userDetails.username)

        val bookings = bookingService.getAllBookings(user.id!!)
            print("\n Booking $bookings")
        return ResponseEntity.ok(bookings)
        //val bookings = user.bookings
    }

    @GetMapping("/{bookingId}")
    fun getBookingById(@PathVariable bookingId: Long) = bookingService.getBookingById(bookingId)

    @PutMapping("/{bookingId}/cancel")
    fun cancelBooking(@PathVariable bookingId: Long, @RequestBody @Valid bookingCancellationRequest: BookingCancellationRequest  ): Int = try {
        print("cancel booking...")
        bookingService.cancelBookingById(
            bookingId,
            bookingCancellationRequest.cancellationReason
        )
    } catch (e: Exception){
        print(e.message)
    } as Int


    @PutMapping("/{bookingId}/accept")
    fun acceptBooking(@PathVariable bookingId: Long ): Int = try {
        print("accepting booking...")
        bookingService.acceptBookingById(
            bookingId
        )
    } catch (e: Exception){
        print(e.message)
    } as Int

    @PutMapping("/{bookingId}/decline")
    fun declineBooking(@PathVariable bookingId: Long ): Int = try {
        print("accepting booking...")
        bookingService.declineBookingById(
            bookingId
        )
    } catch (e: Exception){
        print(e.message)
    } as Int
//    @PutMapping("/bookingId")
//    fun updateBooking(@PathVariable("bookingId") bookingId: Int, @RequestBody booking: BookingRequest) = bookingService.updateBookingById(
//        booking = booking,
//        id = bookingId
//    )
}