package com.kgoro.sangoma_link.booking


import com.kgoro.sangoma_link.sangoma_profile.SangomaProfileRepository
import com.kgoro.sangoma_link.service.SangomaServiceRepository
import com.kgoro.sangoma_link.user.UserRepository
import com.kgoro.sangoma_link.user.enums.BookingStatus
import com.kgoro.sangoma_link.user.enums.UserType
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.Duration

@Service
class BookingService (
    val bookingRepository: BookingRepository,
    val sangomaRepository: SangomaProfileRepository,
    val sangomaServiceRepository: SangomaServiceRepository,
    val userRepository: UserRepository
){
    fun getAllBookings(id: Long): List<BookingResponse> {
        val user = userRepository.findById(id).orElseThrow { NoSuchElementException("User with ID $id not found") }

        if(user.userType != UserType.Sangoma){
            print("This is a normal user")
            val userBookings = bookingRepository.findBookingByUserId(user?.id!!)
            return userBookings.stream().map { userBooking -> BookingResponse(
                bookingId = userBooking.id,
                sangomaName = userBooking.sangoma.user.getFullName(),
                sangomaProfileImage = userBooking.sangoma.user.profileImageUrl,
                sangomaPhoneNumber = userBooking.sangoma.user.phoneNumber,
                serviceName = userBooking.service.name,
                scheduledDateTime = userBooking.scheduledFor,
                duration = Duration.between(userBooking.scheduledUntil, userBooking.scheduledFor).toMinutes(),
                totalAmount = userBooking.totalAmount,
                bookingStatus = userBooking.bookingStatus,
                location = userBooking.location,
                customerNotes = userBooking.customerNotes,
                sangomaNotes = userBooking.sangomaNotes,
                paymentStatus = userBooking.paymentStatus,
                cancellationReason = userBooking.cancellationReason,
                userType = user.userType
            ) }.toList()
        }

        val sangomaBookings  = bookingRepository.findBookingBySangomaId(user.id)
        print("finding bookings for sangoma ${user.id}\n")

        //print("sangomaBookings $sangomaBookings")

        return sangomaBookings.stream().map { sangomaBooking -> BookingResponse(
            bookingId = sangomaBooking.id,
            sangomaName = sangomaBooking.sangoma.user.getFullName(),
            sangomaProfileImage = sangomaBooking.sangoma.user.profileImageUrl,
            sangomaPhoneNumber = sangomaBooking.sangoma.user.phoneNumber,
            serviceName = sangomaBooking.service.name,
            scheduledDateTime = sangomaBooking.scheduledFor,
            duration = Duration.between(sangomaBooking.scheduledUntil, sangomaBooking.scheduledFor).abs().toMinutes(),
            totalAmount = sangomaBooking.totalAmount,
            bookingStatus = sangomaBooking.bookingStatus,
            location = sangomaBooking.location,
            customerNotes = sangomaBooking.customerNotes,
            sangomaNotes = sangomaBooking.sangomaNotes,
            paymentStatus = sangomaBooking.paymentStatus,
            cancellationReason = sangomaBooking.cancellationReason,
            userType = user.userType
        )  }.toList()
    }

    fun getBookingById(id: Long): Booking = bookingRepository.getBookingById(id)

    fun createBooking(booking: BookingRequest) {
            val user = userRepository.findByEmail(booking.sub)
            val sangoma = sangomaRepository.findById(booking.healerId).orElseThrow { NoSuchElementException("Profile with ID ${booking.healerId} not found") }
            val service = sangomaServiceRepository.findServiceByName(booking.serviceName).orElseThrow{ NoSuchElementException("Service with name ${booking.serviceName} not found")}
//           println(service.)

            val newBooking = Booking(
                id = 0,
                customerId = user?.id!!,
                sangoma = sangoma,
                service = service,
                bookingStatus = booking.bookingStatus,
                scheduledFor = booking.scheduledFor,
                scheduledUntil = booking.scheduledUntil,
                totalAmount = booking.totalAmount,
                currency = booking.currency,
                paymentStatus = booking.paymentStatus,
                customerNotes = booking.customerNotes,
                sangomaNotes = booking.sangomaNotes,
                meetingLink = booking.meetingLink,
                location = booking.location,
                createdAt = LocalDateTime.now(),
                updatedAt = null,
                cancelledAt = null,
                cancellationReason = null,
                userId = user.id!!
            )
        bookingRepository.save<Booking>(newBooking)
    }

//    fun updateBookingById(booking: BookingRequest, id: Int) = bookingRepository.updateById(booking, id)

    fun cancelBookingById(id: Long, cancellationReason: String): Int = bookingRepository.cancelById(
        bookingId= id,
        cancellationReason = cancellationReason,
        bookingStatus = BookingStatus.CANCELLED,
        cancelledAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now(),
    )

    fun acceptBookingById(bookingId: Long): Int = bookingRepository.updateBookingById(
        bookingId= bookingId,
        bookingStatus = BookingStatus.CONFIRMED,
        updatedAt = LocalDateTime.now()
    )

    fun declineBookingById(bookingId: Long): Int = bookingRepository.updateBookingById(
        bookingId= bookingId,
        bookingStatus = BookingStatus.DECLINED,
        updatedAt = LocalDateTime.now()
    )
}