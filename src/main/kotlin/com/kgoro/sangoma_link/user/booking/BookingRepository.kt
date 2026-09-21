package com.kgoro.sangoma_link.user.booking

//import com.kgoro.sangoma_link.user.enums.BookingStatus
//import jakarta.transaction.Transactional
//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.data.jpa.repository.Modifying
//import org.springframework.data.jpa.repository.Query
//import org.springframework.data.repository.query.Param
//import org.springframework.stereotype.Repository
//import java.time.LocalDateTime

//@Repository
//interface BookingRepository: JpaRepository<Booking, Int>{
//    fun getBookingById(id: Long): Booking
//
////    @Transactional
////    @Modifying
////    @Query("Update Bookings b SET b.cancellationReason = :cancellationReason WHERE b.id = :bookingId")
////    fun updateById(booking: BookingRequest, id: Int){
////        var updateBooking = getBookingById(id)
////
////        updateBooking.userId = booking.userId
////        updateBooking.sangoma = booking.sangoma
////        updateBooking.bookingStatus = booking.bookingStatus
////        updateBooking.scheduledFor = booking.scheduledFor
////        updateBooking.scheduledUntil = booking.scheduledUntil
////        updateBooking.totalAmount = booking.totalAmount
////        updateBooking.paymentStatus = booking.paymentStatus
////        updateBooking.customerNotes = booking.customerNotes
////        updateBooking.sangomaNotes = booking.sangomaNotes
////        updateBooking.meetingLink = booking.meetingLink
////        updateBooking.location = booking.location
////        updateBooking.cancelledAt = booking.cancelledAt
////        updateBooking.cancellationReason = booking.cancellationReason
////        updateBooking.updatedAt = LocalDateTime.now()
////
////
////        this.save<Booking>(updateBooking)
////    }
//
//    @Transactional
//    @Modifying
//    @Query("""
//        UPDATE Booking b
//        SET b.cancellationReason = :cancellationReason,
//            b.bookingStatus = :bookingStatus,
//            b.cancelledAt = :cancelledAt
//        WHERE b.id = :bookingId
//    """)
//    fun cancelById(
//        @Param("bookingId") bookingId: Long,
//        @Param("cancellationReason") cancellationReason: String,
//        @Param("bookingStatus") bookingStatus: BookingStatus,
//        @Param("cancelledAt") cancelledAt: LocalDateTime,
//        @Param("updatedAt") updatedAt: LocalDateTime
//    ): Int
//
//    fun findBookingByUserId(userId: Long): List<Booking>
//    fun findBookingBySangomaId(id: Long?): List<Booking>
//    @Transactional
//    @Modifying
//    @Query("""
//        UPDATE Booking b
//        SET b.bookingStatus = :bookingStatus,
//            b.updatedAt = :updatedAt
//        WHERE b.id = :bookingId
//    """)
//
//    fun updateBookingById(
//        @Param("bookingId") bookingId: Long,
//        @Param("bookingStatus") bookingStatus: BookingStatus,
//        @Param("updatedAt") updatedAt: LocalDateTime
//    ): Int
////    {
////        var updateBooking = getBookingById(id)
////
////        updateBooking.cancelledAt = LocalDateTime.now()
////        updateBooking.cancellationReason = cancellationReason
////
////        this.save<Booking>(updateBooking)
////    }
//}

 import com.kgoro.sangoma_link.user.enums.BookingStatus
 import org.springframework.data.jpa.repository.JpaRepository
 import org.springframework.data.jpa.repository.Modifying
 import org.springframework.data.jpa.repository.Query
 import org.springframework.data.repository.query.Param
 import org.springframework.stereotype.Repository
 import java.time.OffsetDateTime

@Repository
 interface BookingRepository : JpaRepository<Booking, Long> {

     @Query("""
  SELECT * FROM bookings b
  WHERE b.user_id = :userId
  AND b.scheduled_for >= :fromDate
  ORDER BY b.scheduled_for DESC
  """, nativeQuery = true)
     fun findByUserId(
         @Param("userId") userId: Long,
         @Param("fromDate") fromDate: OffsetDateTime = OffsetDateTime.now().minusMonths(3)
     ): List<Booking>

     @Query("""
  SELECT * FROM bookings b
  WHERE b.sangoma_id = :userId
  AND b.scheduled_for >= :fromDate
  ORDER BY b.scheduled_for DESC
  """, nativeQuery = true)
     fun findBookingBySangomaId(
         @Param("userId") userId: Long,
         @Param("fromDate") fromDate: OffsetDateTime = OffsetDateTime.now().minusMonths(3)
     ): List<Booking>
     @Query("""
  SELECT * FROM bookings b
  WHERE b.healer_id = :healerId
  AND b.scheduled_for >= :fromDate
  ORDER BY b.scheduled_for DESC
  """, nativeQuery = true)
     fun findByHealerId(
         @Param("sangomaId") sangomaId: Long,
         @Param("fromDate") fromDate: OffsetDateTime = OffsetDateTime.now().minusMonths(3)
     ): List<Booking>

     @Modifying
     @Query("""
  UPDATE bookings
  SET booking_status = :status,
  updated_at = CURRENT_TIMESTAMP
  WHERE id = :bookingId
  AND user_id = :userId
  """, nativeQuery = true)
     fun updateBookingStatus(
         @Param("bookingId") bookingId: Long,
         @Param("userId") userId: Long,
         @Param("status") status: BookingStatus
     ): Int

     @Query("""
  SELECT COUNT(*) > 0 FROM bookings
  WHERE slot_id = :slotId
  AND sangoma_id = :sangomaId
  AND booking_status IN ('PENDING', 'CONFIRMED')
  AND scheduled_for = :startTime
  """, nativeQuery = true)
     fun isSlotBooked(@Param("slotId") slotId: Long, @Param("sangomaId") sangomaId: Long, @Param("startTime") startTime: OffsetDateTime): Boolean

 }
