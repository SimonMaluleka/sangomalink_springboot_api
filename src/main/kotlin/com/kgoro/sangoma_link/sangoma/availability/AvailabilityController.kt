package com.kgoro.sangoma_link.sangoma.availability

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/healers/{sangomaId}/availability")
class AvailabilityController(private val svc: AvailabilityService) {

    @GetMapping
    fun getAvailability(@PathVariable sangomaId: Long): ResponseEntity<List<DayAvailabilityDto>> {
        val list = svc.loadAvailability(sangomaId)
        return ResponseEntity.ok(list)
    }

    @PutMapping
    fun putAvailability(@PathVariable sangomaId: Long, @RequestBody payload: List<DayAvailabilityDto>): ResponseEntity<Unit> {
        print("\n Received payload: $payload")
        svc.saveAvailability(sangomaId, payload)
        return ResponseEntity.noContent().build()
    }

//    @PostMapping("/weekly")
//    fun setWeeklyAvailability(
//        @PathVariable healerId: Long,
//        @RequestBody request: WeeklyScheduleRequest
//    ): ResponseEntity<WeekklyScheduleResponse> {
//        val availabilities = availabilityService.setWeeklyAvailability(healerId, request.availabilities)
//        val response = WeekklyScheduleResponse(
//            healerId = healerId,
//            availabilities = availabilities,
//            message = "Your availability is now live"
//        )
//        return ResponseEntity(response, HttpStatus.CREATED)
//    }
//
//    @GetMapping
//    fun getWeeklyAvailability(@PathVariable healerId: Long): ResponseEntity<List<AvailabilityResponse>> {
//        return ResponseEntity.ok(availabilityService.getWeeklyAvailability(healerId))
//    }
//
//    @GetMapping("/day/{dayOfWeek}")
//    fun getAvailabilityForDay(
//        @PathVariable healerId: Long,
//        @PathVariable dayOfWeek: String
//    ): ResponseEntity<AvailabilityResponse?> {
//        val availability = availabilityService.getAvailabilityForDay(healerId, dayOfWeek)
//        return if (availability != null) {
//            ResponseEntity.ok(availability)
//        } else {
//            ResponseEntity.noContent().build()
//        }
//    }
}