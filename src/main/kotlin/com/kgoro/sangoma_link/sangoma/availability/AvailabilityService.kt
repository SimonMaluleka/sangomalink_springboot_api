package com.kgoro.sangoma_link.sangoma.availability

import java.time.OffsetDateTime
import java.util.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalTime
import kotlin.toString

@Service
class AvailabilityService(private val repo: AvailabilityRepository) {

    fun loadAvailability(sangomaId: Long): List<DayAvailabilityDto> =
        repo.findBySangomaId(sangomaId)
            .sortedBy { it.dayOfWeek }
            .map { DayAvailabilityDto(it.dayOfWeek, it.enabled, it.recurring, it.hours) }

    @Transactional
    fun saveAvailability(sangomaId: Long, payload: List<DayAvailabilityDto>) {
        // Upsert each day
        val incomingDays = payload.map { it.dayOfWeek }.toSet()
        for (dto in payload) {
            val existing = repo.findBySangomaIdAndDayOfWeek(sangomaId, dto.dayOfWeek)
            val now = OffsetDateTime.now()
            if (existing != null) {
                existing.enabled = dto.enabled
                existing.recurring = dto.recurring
                existing.hours = dto.hours
                existing.updatedAt = now
                repo.save(existing)
            } else {
                val e = Availability(
                    sangomaId = sangomaId,
                    dayOfWeek = dto.dayOfWeek,
                    enabled = dto.enabled,
                    recurring = dto.recurring,
                    hours = dto.hours,
                    createdAt = now,
                    updatedAt = now
                )
                repo.save(e)
            }
        }
        // Remove any days not included in payload (optional)
        repo.deleteBySangomaIdAndDayOfWeekNotIn(sangomaId, incomingDays)
    }

//    fun setWeeklyAvailability(healerId: Long, requests: List<AvailabilityRequest>): List<AvailabilityResponse> {
//        val healer = healerRepository.findById(healerId)
//            .orElseThrow { IllegalArgumentException("Healer not found with id: $healerId") }
//
//        // Clear existing availabilities
//        val existing = availabilityRepository.findByHealer(healer)
//        availabilityRepository.deleteAll(existing)
//
//        // Save new availabilities
//        val availabilities = requests.map { request ->
//            Availability(
//                healer = healer,
//                dayOfWeek = DayOfWeek.valueOf(request.dayOfWeek.uppercase()),
//                startTime = LocalTime.parse(request.startTime),
//                endTime = LocalTime.parse(request.endTime),
//                slotDuration = request.slotDuration,
//                breakStartTime = request.breakStartTime?.let { LocalTime.parse(it) },
//                breakEndTime = request.breakEndTime?.let { LocalTime.parse(it) }
//            )
//        }
//
//        val saved = availabilityRepository.saveAll(availabilities)
//        return saved.map { it.toResponse() }
//    }
//
//    fun getWeeklyAvailability(healerId: Long): List<AvailabilityResponse> {
//        val healer = healerRepository.findById(healerId)
//            .orElseThrow { IllegalArgumentException("Healer not found with id: $healerId") }
//
//        val availabilities = availabilityRepository.findAllByHealerOrderByDayOfWeek(healer)
//        return availabilities.map { it.toResponse() }
//    }
//
//    fun getAvailabilityForDay(healerId: Long, dayOfWeek: String): AvailabilityResponse? {
//        val healer = healerRepository.findById(healerId)
//            .orElseThrow { IllegalArgumentException("Healer not found with id: $healerId") }
//
//        val day = DayOfWeek.valueOf(dayOfWeek.uppercase())
//        val availability = availabilityRepository.findByHealerAndDayOfWeek(healer, day)
//        return availability?.toResponse()
//    }
//
//    private fun Availability.toResponse(): AvailabilityResponse {
//        return AvailabilityResponse(
//            id = this.id,
//            dayOfWeek = this.dayOfWeek.toString(),
//            startTime = this.startTime.toString(),
//            endTime = this.endTime.toString(),
//            slotDuration = this.slotDuration,
//            breakStartTime = this.breakStartTime?.toString(),
//            breakEndTime = this.breakEndTime?.toString()
//        )
//    }
}
//import com.kgoro.sangoma_link.user.booking.BookingRepository
//import org.springframework.dao.OptimisticLockingFailureException
//
//import java.time.*

//@Service
//class SlotAvailabilityService(
//    private val healerSlotRepository: AvailabilityRepository,
//    private val bookingRepository: BookingRepository
//) {
//
//    @Transactional
//    fun toggleSlotAvailability(
//        slotId: Long,
//        sangomaId: Long,
//        isAvailable: Boolean,
//        version: Long
//    ): Boolean {
//        return try {
//            val updated = healerSlotRepository.updateAvailability(slotId, sangomaId, isAvailable, version)
//            if (updated > 0) {
//                true
//            } else {
//                throw OptimisticLockingFailureException("Slot was modified by another operation")
//            }
//        } catch (e: OptimisticLockingFailureException) {
//// Handle concurrent modification
//            false
//        }
//    }
//
//    @Transactional
//    fun generateRecurringSlots(
//        sangomaId: Long,
//        startDate: LocalDate,
//        endDate: LocalDate,
//        startTime: LocalTime,
//        endTime: LocalTime,
//        recurrencePattern: RecurrencePattern,
//        daysOfWeek: Set<DayOfWeek> = setOf()
//    ): List<Slot> {
//        val slots = mutableListOf<Slot>()
//        var currentDate = startDate
//
//        while (currentDate <= endDate) {
//// Check if we should create slot for this date based on recurrence
//            val shouldCreate = when (recurrencePattern) {
//                RecurrencePattern.DAILY -> true
//                RecurrencePattern.WEEKLY -> daysOfWeek.contains(currentDate.dayOfWeek)
//                RecurrencePattern.MONTHLY -> currentDate.dayOfMonth == startDate.dayOfMonth
//            }
//
//            if (shouldCreate) {
//                val slot = Slot(
//                    id = 0,
//                    sangomaId = sangomaId,
//                    startTime = currentDate.atTime(startTime).atZone(ZoneId.systemDefault()).toOffsetDateTime(),
//                    endTime = currentDate.atTime(endTime).atZone(ZoneId.systemDefault()).toOffsetDateTime(),
//                    isRecurring = true,
//                    recurrencePattern = recurrencePattern.name
//                )
//                slots.add(healerSlotRepository.save(slot))
//            }
//
//            currentDate = currentDate.plusDays(1)
//        }
//
//        return slots
//    }
//
//    fun getSlotsWithBookingStatus(
//        sangomaId: Long,
//        startDate: OffsetDateTime,
//        endDate: OffsetDateTime
//    ): List<SlotWithStatus> {
//        val slots = healerSlotRepository.getSlotsWithAvailabilityStatus(sangomaId, startDate, endDate)
//
//        return slots.map { slot ->
//            val isBooked = bookingRepository.isSlotBooked(slot.id, sangomaId, startDate)
//            SlotWithStatus(
//                slot = slot,
//                isBooked = isBooked,
//                isAvailable = slot.isAvailable && !isBooked
//            )
//        }
//    }
//}
//
//enum class RecurrencePattern {
//    DAILY, WEEKLY, MONTHLY
//}
//
//data class SlotWithStatus(
//    val slot: Slot,
//    val isBooked: Boolean,
//    val isAvailable: Boolean
//)