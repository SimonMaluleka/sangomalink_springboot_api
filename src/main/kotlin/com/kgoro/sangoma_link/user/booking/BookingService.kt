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

//@Service
//class BookingService (
//    val bookingRepository: BookingRepository,
//    val sangomaRepository: SangomaProfileRepository,
//    val sangomaServiceRepository: SangomaServiceRepository,
//    val userRepository: UserRepository
//){
//    fun getAllBookings(id: Long): List<BookingResponse> {
//        val user = userRepository.findById(id).orElseThrow { NoSuchElementException("User with ID $id not found") }
//
//        if(user.userType != UserType.Sangoma){
//            print("This is a normal user")
//            val userBookings = bookingRepository.findBookingByUserId(user?.id!!)
//            return userBookings.stream().map { userBooking -> BookingResponse(
//                bookingId = userBooking.id,
//                sangomaName = userBooking.sangoma.user.getFullName(),
//                sangomaProfileImage = userBooking.sangoma.user.profileImageUrl,
//                sangomaPhoneNumber = userBooking.sangoma.user.phoneNumber,
//                serviceName = userBooking.service.name,
//                scheduledDateTime = userBooking.scheduledFor,
//                duration = Duration.between(userBooking.scheduledUntil, userBooking.scheduledFor).toMinutes(),
//                totalAmount = userBooking.totalAmount,
//                bookingStatus = userBooking.bookingStatus,
//                location = userBooking.location,
//                customerNotes = userBooking.customerNotes,
//                sangomaNotes = userBooking.sangomaNotes,
//                paymentStatus = userBooking.paymentStatus,
//                cancellationReason = userBooking.cancellationReason,
//                userType = user.userType
//            ) }.toList()
//        }
//
//        val sangomaBookings  = bookingRepository.findBookingBySangomaId(user.id)
//        print("finding bookings for sangoma ${user.id}\n")
//
//        //print("sangomaBookings $sangomaBookings")
//
//        return sangomaBookings.stream().map { sangomaBooking -> BookingResponse(
//            bookingId = sangomaBooking.id,
//            sangomaName = sangomaBooking.sangoma.user.getFullName(),
//            sangomaProfileImage = sangomaBooking.sangoma.user.profileImageUrl,
//            sangomaPhoneNumber = sangomaBooking.sangoma.user.phoneNumber,
//            serviceName = sangomaBooking.service.name,
//            scheduledDateTime = sangomaBooking.scheduledFor,
//            duration = Duration.between(sangomaBooking.scheduledUntil, sangomaBooking.scheduledFor).abs().toMinutes(),
//            totalAmount = sangomaBooking.totalAmount,
//            bookingStatus = sangomaBooking.bookingStatus,
//            location = sangomaBooking.location,
//            customerNotes = sangomaBooking.customerNotes,
//            sangomaNotes = sangomaBooking.sangomaNotes,
//            paymentStatus = sangomaBooking.paymentStatus,
//            cancellationReason = sangomaBooking.cancellationReason,
//            userType = user.userType
//        )  }.toList()
//    }
//
//    fun getBookingById(id: Long): Booking = bookingRepository.getBookingById(id)
//
//    fun createBooking(booking: BookingRequest) {
//            val user = userRepository.findByEmail(booking.sub)
//            val sangoma = sangomaRepository.findById(booking.healerId).orElseThrow { NoSuchElementException("Profile with ID ${booking.healerId} not found") }
//            val service = sangomaServiceRepository.findServiceByName(booking.serviceName).orElseThrow{ NoSuchElementException("Service with name ${booking.serviceName} not found")}
////           println(service.)
//
//            val newBooking = Booking(
//                id = 0,
//                customerId = user?.id!!,
//                sangoma = sangoma,
//                service = service,
//                bookingStatus = booking.bookingStatus,
//                scheduledFor = booking.scheduledFor,
//                scheduledUntil = booking.scheduledUntil,
//                totalAmount = booking.totalAmount,
//                currency = booking.currency,
//                paymentStatus = booking.paymentStatus,
//                customerNotes = booking.customerNotes,
//                sangomaNotes = booking.sangomaNotes,
//                meetingLink = booking.meetingLink,
//                location = booking.location,
//                createdAt = LocalDateTime.now(),
//                updatedAt = null,
//                cancelledAt = null,
//                cancellationReason = null,
//                userId = user.id!!
//            )
//        bookingRepository.save<Booking>(newBooking)
//    }
//
////    fun updateBookingById(booking: BookingRequest, id: Int) = bookingRepository.updateById(booking, id)
//
//    fun cancelBookingById(id: Long, cancellationReason: String): Int = bookingRepository.cancelById(
//        bookingId= id,
//        cancellationReason = cancellationReason,
//        bookingStatus = BookingStatus.CANCELLED,
//        cancelledAt = LocalDateTime.now(),
//        updatedAt = LocalDateTime.now(),
//    )
//
//    fun acceptBookingById(bookingId: Long): Int = bookingRepository.updateBookingById(
//        bookingId= bookingId,
//        bookingStatus = BookingStatus.CONFIRMED,
//        updatedAt = LocalDateTime.now()
//    )
//
//    fun declineBookingById(bookingId: Long): Int = bookingRepository.updateBookingById(
//        bookingId= bookingId,
//        bookingStatus = BookingStatus.DECLINED,
//        updatedAt = LocalDateTime.now()
//
//
//
//    )
//}


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

//@Transactional
//fun createBooking(request: BookingRequest): BookingResult {
//        val sangoma = profileRepository.findById(request.healerId).orElseThrow { NoSuchElementException("Profile with ID ${request.healerId} not found") }
//        val service = practiceRepository.findServiceByName(request.serviceName).orElseThrow{ NoSuchElementException("Service with name ${request.serviceName} not found")}
//        val userId = userRepository.findByEmail(request.sub)?.id!!
//
//        print("\nUser ID: $userId \n Service: $service \n Sangoma $sangoma")
//
//
//
////        when(request.scheduledFor.hour){
////            5 ->  slotId = 1
////            6 ->  slotId = 2
////            7 ->  slotId = 3
////            8 ->  slotId = 4
////            9 ->  slotId = 5
////            10 -> slotId = 6
////            11 -> slotId = 7
////            12 -> slotId = 8
////            13 -> slotId = 9
////            14 -> slotId = 10
////            15 -> slotId = 11
////            16 -> slotId = 12
////            17 -> slotId = 13
////            18 -> slotId = 14
////            19 -> slotId = 15
////            20 -> slotId = 16
////            21 -> slotId = 17
////            22 -> slotId = 18
////            23 -> slotId = 19
////            24 -> slotId = 20
////            else -> 0
////        }
////        if(request.scheduledFor.hour == 5){
////            slotId = 1
////            print("\n SlotID: $slotId \n")
////        }
//
//        // Step 1: Check if slot exists and is available
//        val slot = slotRepository.findBySangomaId(sangoma.id)
//       // .orElseThrow { IllegalArgumentException("Slot not found") }
//
//        print("\n Slot: $slot")
//        // Step 2: Validate slot time (cannot book past slots)
//        if (slot.startTime.isBefore(OffsetDateTime.now())) {
//            return BookingResult.Error("Cannot book past time slots")
//        }
//
//
//
//        // Step 3: Check if slot is already booked using pessimistic lock
//        val isBooked = bookingRepository.isSlotBooked(slot.id, sangoma.id, slot.startTime
//          )
//        if (isBooked) {
//            return BookingResult.Error("Slot is already booked")
//        }
//
//        // Step 4: Check if slot is available
//        if (!slot.isAvailable) {
//            return BookingResult.Error("Healer is not available for this slot")
//        }
//
//        if(slot.startTime.dayOfMonth == request.scheduledFor.dayOfMonth){
//            // Step 5: Create booking with optimistic locking
//            val newBooking = Booking(
//                id = 0,
//                userId = userId,
//                slot = slot,
//                sangoma = sangoma,
//                practice = service,
//                bookingStatus = BookingStatus.PENDING,
//                scheduledFor = request.scheduledFor,
//                scheduledUntil = request.scheduledUntil,
//                totalAmount = request.totalAmount,
//                currency = request.currency,
//                paymentStatus = request.paymentStatus,
//                customerNotes = request.customerNotes,
//                sangomaNotes = request.sangomaNotes,
//                meetingLink = request.meetingLink,
//                location = request.location,
//                // createdAt = LocalDateTime.now(),
//                //        updatedAt = null,
//                //        cancelledAt = null,
//                cancellationReason = null
//            )
//
//            return try {
//                print("Starting")
//                val savedBooking = bookingRepository.save(newBooking)
//                print("start notification")
//                eventPublisher.publishEvent(
//                    BookingCreatedEvent(
//                        appointmentId = savedBooking.id,
//                        healerId = savedBooking.sangoma.id,
//                        clientId = savedBooking.userId,
//                        title = savedBooking.practice.name,
//                        startTime = savedBooking.scheduledFor,
//                        actionUrl = "/bookings/${savedBooking.id}",
//                        relatedEntityType = "booking",
//                        relatedEntityId = savedBooking.id,
//                        metadata = null
//                    )
//                )
//
//                return BookingResult.Success(savedBooking)
//            } catch (e: Exception) {
//                // Handle unique constraint violation
//                if (e.message?.contains("duplicate key") == true) {
//                    BookingResult.Error("Slot was booked by another user. Please try another slot.")
//                } else {
//                    throw e
//                }
//            }
//        }
//        return BookingResult.Error("no slot for the desired day")
//    }

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
//@Transactional
//fun rescheduleBooking(
//    bookingId: Long,
//    userId: Long,
//    newSlotId: Long
//): BookingResult {
//    // Step 1: Cancel existing booking
//    val cancelResult = cancelBooking(bookingId, userId)
//    if (cancelResult is BookingResult.Error) {
//    return cancelResult
//    }
//
//    // Step 2: Create new booking
//    val oldBooking = (cancelResult as BookingResult.Success).booking
//        return createBooking(userId, BookingRequest(
//            sub = oldBooking.,
//            healerId = oldBooking.sa,
//            serviceName = TODO(),
//            bookingStatus = TODO(),
//            scheduledFor = TODO(),
//            scheduledUntil = TODO(),
//            totalAmount = TODO(),
//            currency = TODO(),
//            paymentStatus = TODO(),
//            customerNotes = TODO(),
//            sangomaNotes = TODO(),
//            meetingLink = TODO(),
//            location = TODO(),
//            createdAt = TODO(),
//            updatedAt = TODO(),
//            cancelledAt = TODO(),
//            cancellationReason = TODO()
//        ))
//    }
}

sealed class BookingResult {
    data class Success(val booking: Booking) : BookingResult()
    data class Error(val message: String) : BookingResult()
}