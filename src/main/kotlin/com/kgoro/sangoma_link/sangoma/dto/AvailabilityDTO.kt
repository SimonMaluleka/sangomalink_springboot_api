package com.sangoma.dto

import java.time.DayOfWeek
import java.time.LocalTime

data class AvailabilityRequest(
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String,
    val slotDuration: Int = 60,
    val breakStartTime: String? = null,
    val breakEndTime: String? = null
)

data class AvailabilityResponse(
    val id: Long,
    val dayOfWeek: String,
    val startTime: String,
    val endTime: String,
    val slotDuration: Int,
    val breakStartTime: String?,
    val breakEndTime: String?
)

data class WeeklyScheduleRequest(
    val availabilities: List<AvailabilityRequest>
)

data class WeekklyScheduleResponse(
    val healerId: Long,
    val availabilities: List<AvailabilityResponse>,
    val message: String
)

data class ScheduleSlotsResponse(
    val slots: List<ScheduleSlotResponse>
)

data class ScheduleSlotResponse(
    val id: Long,
    val slotDate: String,
    val startTime: String,
    val endTime: String,
    val status: String
)
