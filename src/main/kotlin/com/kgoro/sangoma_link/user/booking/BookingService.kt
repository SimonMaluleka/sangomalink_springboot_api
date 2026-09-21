package com.kgoro.sangoma_link.user.booking


//import com.kgoro.sangoma_link.sangoma_profile.SangomaProfileRepository
//import com.kgoro.sangoma_link.service.SangomaServiceRepository
//import com.kgoro.sangoma_link.user.UserRepository
//import com.kgoro.sangoma_link.user.enums.BookingStatus
import com.kgoro.sangoma_link.common.notification.NotificationService
import com.kgoro.sangoma_link.user.enums.UserType
//import org.springframework.stereotype.Service
//import java.time.LocalDateTime
import java.time.Duration


// BookingService.kt

import com.kgoro.sangoma_link.sangoma.profile.SangomaProfileRepository
import com.kgoro.sangoma_link.sangoma.availability.AvailabilityRepository
import com.kgoro.sangoma_link.sangoma.practice.PracticeRepository
import com.kgoro.sangoma_link.user.UserRepository
import com.kgoro.sangoma_link.user.enums.BookingStatus
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.*


@Service
class BookingService(
    private val bookingRepository: BookingRepository,
    private val profileRepository: SangomaProfileRepository,
    private val practiceRepository: PracticeRepository,
    private val userRepository: UserRepository,
    private val availabilityRepository: AvailabilityRepository,
    private val notificationService: NotificationService,
    private val eventPublisher: ApplicationEventPublisher
) {
    fun getAllBookings(id: Long): List<BookingResponse> {
        val user = userRepository.findById(id).orElseThrow { NoSuchElementException("User with ID $id not found") }

        if(user.userType != UserType.Sangoma){
            print("This is a normal user")
            val userBookings = bookingRepository.findByUserId(user?.id!!)
            return userBookings.stream().map { userBooking -> BookingResponse(
                bookingId = userBooking.id,
                sangomaName = userBooking.sangoma.user.getFullName(),
                sangomaProfileImage = userBooking.sangoma.user.profileImageUrl,
                sangomaPhoneNumber = userBooking.sangoma.user.phoneNumber,
                serviceName = userBooking.practice.name,
                scheduledDateTime = userBooking.scheduledFor,
                duration = Duration.between(userBooking.scheduledUntil, userBooking.scheduledFor).toMinutes(),
                totalAmount = userBooking.totalAmount,
                bookingStatus = userBooking.bookingStatus,
                location = userBooking.location,
                customerNotes = userBooking.customerNotes,
                sangomaNotes = userBooking.sangomaNotes,
                paymentStatus = userBooking.paymentStatus,
                cancellationReason = userBooking.cancellationReason,
                userType = user.userType,
                serviceType = userBooking.practice.serviceType,
                customerName = user.getFullName(),
                customerImage = user.profileImageUrl!!,
                customerPhone = user.phoneNumber!!,
                customerEmail = user.email,
                meetingLink = userBooking.meetingLink!!
            ) }.toList()
        }

        val sangomaBookings  = bookingRepository.findBookingBySangomaId(user?.id!!)
        print("finding bookings for sangoma ${user.id}\n")

        return sangomaBookings.stream().map { sangomaBooking -> BookingResponse(
            bookingId = sangomaBooking.id,
            sangomaName = sangomaBooking.sangoma.user.getFullName(),
            sangomaProfileImage = sangomaBooking.sangoma.user.profileImageUrl,
            sangomaPhoneNumber = sangomaBooking.sangoma.user.phoneNumber,
            serviceName = sangomaBooking.practice.name,
            scheduledDateTime = sangomaBooking.scheduledFor,
            duration = Duration.between(sangomaBooking.scheduledUntil, sangomaBooking.scheduledFor).abs().toMinutes(),
            totalAmount = sangomaBooking.totalAmount,
            bookingStatus = sangomaBooking.bookingStatus,
            location = sangomaBooking.location,
            customerNotes = sangomaBooking.customerNotes,
            sangomaNotes = sangomaBooking.sangomaNotes,
            paymentStatus = sangomaBooking.paymentStatus,
            cancellationReason = sangomaBooking.cancellationReason,
            userType = user.userType,
            serviceType = sangomaBooking.practice.serviceType,
            customerName = user.getFullName(),
            customerImage = user.profileImageUrl!!,
            customerPhone = user.phoneNumber!!,
            customerEmail = user.email,
            meetingLink = sangomaBooking.meetingLink!!
        )  }.toList()
    }


@Transactional
fun cancelBooking(bookingId: Long, userId: Long): BookingResult {
        val booking = bookingRepository.findById(bookingId)
        .orElseThrow { IllegalArgumentException("Booking not found") }

        val saOffset = ZoneOffset.of("+02:00")

        // Verify ownership
        if (booking.userId != userId) {
            return BookingResult.Error("Unauthorized to cancel this booking")
        }

        // Check if cancellation is within allowed time (e.g., 24 hours before)
        val hoursUntilAppointment = booking.scheduledFor
        .toEpochSecond(saOffset) - OffsetDateTime.now().toEpochSecond()

        if (hoursUntilAppointment < 24 * 3600) {
            return BookingResult.Error("Cannot cancel within 24 hours of appointment")
        }

        val updated = bookingRepository.updateBookingStatus(bookingId, userId, BookingStatus.CANCELLED)
        return if (updated > 0) {
        BookingResult.Success(booking.copy(bookingStatus = BookingStatus.CANCELLED))
        } else {
        BookingResult.Error("Failed to cancel booking")
    }


}
    fun acceptBookingById(bookingId: Long, sangomaId: Long): BookingResult {
        val booking = bookingRepository.findById(bookingId)
            .orElseThrow { IllegalArgumentException("Booking not found") }

        val saOffset = ZoneOffset.of("+02:00")

        // Verify ownership
        if (booking.sangoma.id != sangomaId) {
            return BookingResult.Error("Unauthorized to accept this booking")
        }

        // Check if cancellation is within allowed time (e.g., 24 hours before)
        val hoursUntilAppointment = booking.scheduledFor
            .toEpochSecond(saOffset) - OffsetDateTime.now().toEpochSecond()

        if (hoursUntilAppointment < 24 * 3600) {
            return BookingResult.Error("Cannot cancel within 24 hours of appointment")
        }

        val updated = bookingRepository.updateBookingStatus(bookingId, booking.userId, BookingStatus.CONFIRMED)
        return if (updated > 0) {
            BookingResult.Success(booking.copy(bookingStatus = BookingStatus.CONFIRMED))
        } else {
            BookingResult.Error("Failed to accept booking")
        }
    }


fun declineBookingById(bookingId: Long, sangomaId: Long): BookingResult {
    val booking = bookingRepository.findById(bookingId)
        .orElseThrow { IllegalArgumentException("Booking not found") }

    val saOffset = ZoneOffset.of("+02:00")

    // Verify ownership
    if (booking.sangoma.id != sangomaId) {
        return BookingResult.Error("Unauthorized to accept this booking")
    }

    // Check if cancellation is within allowed time (e.g., 24 hours before)
    val hoursUntilAppointment = booking.scheduledFor
        .toEpochSecond(saOffset) - OffsetDateTime.now().toEpochSecond()

    if (hoursUntilAppointment < 24 * 3600) {
        return BookingResult.Error("Cannot cancel within 24 hours of appointment")
    }

    val updated = bookingRepository.updateBookingStatus(bookingId, booking.userId, BookingStatus.DECLINED)
    return if (updated > 0) {
        BookingResult.Success(booking.copy(bookingStatus = BookingStatus.DECLINED))
    } else {
        BookingResult.Error("Failed to accept booking")
    }
}
}

sealed class BookingResult {
    data class Success(val booking: Booking) : BookingResult()
    data class Error(val message: String) : BookingResult()
}