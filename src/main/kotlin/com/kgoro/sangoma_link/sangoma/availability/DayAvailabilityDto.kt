package com.kgoro.sangoma_link.sangoma.availability

data class DayAvailabilityDto(
    val dayOfWeek: Int,
    val enabled: Boolean,
    val recurring: Boolean,
    val hours: List<Int>
)