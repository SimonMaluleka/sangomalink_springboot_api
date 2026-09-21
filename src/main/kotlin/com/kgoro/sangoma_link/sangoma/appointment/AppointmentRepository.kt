package com.kgoro.sangoma_link.sangoma.appointment

import com.kgoro.sangoma_link.user.booking.Booking
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface AppointmentRepository: JpaRepository<Booking, Long> {
    @Query("""
      Select * FROM bookings
      WHERE sangoma_id = :sangomaId 
      AND scheduled_for >= CURRENT_DATE
      """, nativeQuery = true)
    fun findUpcomingBookingBySangomaId(
        @Param("sangomaId") sangomaId: Long,
//        @Param("fromDate") fromDate: OffsetDateTime = OffsetDateTime.now()
        ): List<Booking>
}