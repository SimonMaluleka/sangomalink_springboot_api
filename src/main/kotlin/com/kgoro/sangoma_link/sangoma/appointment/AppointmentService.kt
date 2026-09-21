package com.kgoro.sangoma_link.sangoma.appointment

import com.kgoro.sangoma_link.user.UserService
import com.kgoro.sangoma_link.user.booking.BookingResponse
import org.springframework.stereotype.Service

@Service
class AppointmentService (
    val appointmentRepository: AppointmentRepository,
    val userService: UserService,
){
    fun findUpcomingBookingBySangomaId(
        healerId: Long
    ): List<BookingResponse> {
        val apts = appointmentRepository.findUpcomingBookingBySangomaId(healerId)
            //.stream()
            .map {
                booking ->
                BookingResponse(
                    bookingId = booking.id,
                    customerName = userService.getUserById(booking.userId).getFullName() ,
                    customerImage = userService.getUserById(booking.userId).profileImageUrl!!,
                    customerPhone = userService.getUserById(booking.userId).phoneNumber!!,
                    customerEmail = userService.getUserById(booking.userId).email,
                    sangomaName = booking.sangoma.user.getFullName(),
                    sangomaProfileImage = booking.sangoma.user.profileImageUrl,
                    sangomaPhoneNumber = booking.sangoma.user.phoneNumber,
                    serviceName = booking.practice.name,
                    scheduledDateTime = booking.scheduledFor,
                    duration = ((booking.scheduledUntil.hour - booking.scheduledFor.hour) * 60).toLong(),
                    totalAmount = booking.totalAmount,
                    bookingStatus = booking.bookingStatus,
                    meetingLink = booking.meetingLink!!,
                    location = booking.location,
                    customerNotes = booking.customerNotes,
                    sangomaNotes = booking.sangomaNotes,
                    paymentStatus = booking.paymentStatus,
                    cancellationReason = booking.cancellationReason,
                    userType = userService.getUserById(booking.userId).userType,
                    serviceType = booking.practice.serviceType,


                )
            }.toList()
        print("Apts: $apts")
        return apts
    }
}