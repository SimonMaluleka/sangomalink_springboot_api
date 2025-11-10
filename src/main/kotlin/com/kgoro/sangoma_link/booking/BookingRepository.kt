package com.kgoro.sangoma_link.booking

import com.kgoro.sangoma_link.user.enums.BookingStatus
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface BookingRepository: JpaRepository<Booking, Int>{
    fun getBookingById(id: Long): Booking

//    @Transactional
//    @Modifying
//    @Query("Update Bookings b SET b.cancellationReason = :cancellationReason WHERE b.id = :bookingId")
//    fun updateById(booking: BookingRequest, id: Int){
//        var updateBooking = getBookingById(id)
//
//        updateBooking.userId = booking.userId
//        updateBooking.sangoma = booking.sangoma
//        updateBooking.bookingStatus = booking.bookingStatus
//        updateBooking.scheduledFor = booking.scheduledFor
//        updateBooking.scheduledUntil = booking.scheduledUntil
//        updateBooking.totalAmount = booking.totalAmount
//        updateBooking.paymentStatus = booking.paymentStatus
//        updateBooking.customerNotes = booking.customerNotes
//        updateBooking.sangomaNotes = booking.sangomaNotes
//        updateBooking.meetingLink = booking.meetingLink
//        updateBooking.location = booking.location
//        updateBooking.cancelledAt = booking.cancelledAt
//        updateBooking.cancellationReason = booking.cancellationReason
//        updateBooking.updatedAt = LocalDateTime.now()
//
//
//        this.save<Booking>(updateBooking)
//    }

    @Transactional
    @Modifying
    @Query("""
        UPDATE Booking b 
        SET b.cancellationReason = :cancellationReason, 
            b.bookingStatus = :bookingStatus,
            b.cancelledAt = :cancelledAt
        WHERE b.id = :bookingId
    """)
    fun cancelById(
        @Param("bookingId") bookingId: Long,
        @Param("cancellationReason") cancellationReason: String,
        @Param("bookingStatus") bookingStatus: BookingStatus,
        @Param("cancelledAt") cancelledAt: LocalDateTime,
        @Param("updatedAt") updatedAt: LocalDateTime
    ): Int

    fun findBookingByUserId(userId: Long): List<Booking>
    fun findBookingBySangomaId(id: Long?): List<Booking>
    @Transactional
    @Modifying
    @Query("""
        UPDATE Booking b 
        SET b.bookingStatus = :bookingStatus,
            b.updatedAt = :updatedAt
        WHERE b.id = :bookingId
    """)

    fun updateBookingById(
        @Param("bookingId") bookingId: Long,
        @Param("bookingStatus") bookingStatus: BookingStatus,
        @Param("updatedAt") updatedAt: LocalDateTime
    ): Int
//    {
//        var updateBooking = getBookingById(id)
//
//        updateBooking.cancelledAt = LocalDateTime.now()
//        updateBooking.cancellationReason = cancellationReason
//
//        this.save<Booking>(updateBooking)
//    }
}