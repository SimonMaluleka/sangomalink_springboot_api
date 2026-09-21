package com.kgoro.sangoma_link.sangoma.availability

import com.kgoro.sangoma_link.sangoma.profile.Profile
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*
interface AvailabilityRepository : JpaRepository<Availability, Long> {
    fun findBySangomaId(sangomaId: Long): List<Availability>
    fun findBySangomaIdAndDayOfWeek(sangomaId: Long, dayOfWeek: Int): Availability?
    fun deleteBySangomaIdAndDayOfWeekNotIn(sangomaId: Long, dayOfWeeks: Collection<Int>)
}




////import org.springframework.data.jdbc.repository.query.Modifying
////import org.springframework.data.jdbc.repository.query.Query
//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.data.jpa.repository.Modifying
//import org.springframework.data.jpa.repository.Query
//import org.springframework.data.repository.query.Param
//import org.springframework.stereotype.Repository
//import java.time.OffsetDateTime
//import java.util.*
//
//@Repository
//interface AvailabilityRepository : JpaRepository<Slot, Long> {
//
//    @Query("""
//SELECT * FROM healer_slots
//WHERE healer_id = :sangomaId
//AND start_time >= :startDate
//AND end_time <= :endDate
//AND is_available = true
//ORDER BY start_time
//""", nativeQuery = true)
//    fun findAvailableSlots(
//        @Param("sangomaId") sangomaId: UUID,
//        @Param("startDate") startDate: OffsetDateTime,
//        @Param("endDate") endDate: OffsetDateTime
//    ): List<Slot>
//
//    @Query("""
//SELECT hs.* FROM healer_slots hs
//LEFT JOIN bookings b ON hs.id = b.slot_id
//WHERE hs.healer_id = :sangomaId
//AND hs.start_time >= :startDate
//AND hs.end_time <= :endDate
//AND (b.id IS NULL OR b.status != 'CONFIRMED')
//ORDER BY hs.start_time
//""", nativeQuery = true)
//    fun getSlotsWithAvailabilityStatus(
//        @Param("sangomaId") sangomaId: Long,
//        @Param("startDate") startDate: OffsetDateTime,
//        @Param("endDate") endDate: OffsetDateTime
//    ): List<Slot>
//
//    @Modifying
//    @Query("""
//UPDATE healer_slots
//SET is_available = :isAvailable,
//updated_at = CURRENT_TIMESTAMP,
//version = version + 1
//WHERE id = :slotId
//AND healer_id = :sangomaId
//AND version = :version
//""", nativeQuery = true)
//    fun updateAvailability(
//        @Param("slotId") slotId: Long,
//        @Param("sangomaId") sangomaId: Long,
//        @Param("isAvailable") isAvailable: Boolean,
//        @Param("version") version: Long
//    ): Int
//
//    @Query("""
//SELECT * FROM healer_slots
//WHERE healer_id = :sangomaId
//AND start_time > CURRENT_TIMESTAMP
//AND is_available = true
//AND NOT EXISTS (
//SELECT 1 FROM bookings
//WHERE bookings.slot_id = healer_slots.id
//AND bookings.status = 'CONFIRMED'
//)
//ORDER BY start_time
//LIMIT :limit
//""", nativeQuery = true)
//    fun getUpcomingAvailableSlots(
//        @Param("sangomaId") sangomaId: UUID,
//        @Param("limit") limit: Int = 50
//    ): List<Slot>
//}