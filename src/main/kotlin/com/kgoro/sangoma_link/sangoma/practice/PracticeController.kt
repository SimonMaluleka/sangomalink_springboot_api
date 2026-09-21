package com.kgoro.sangoma_link.sangoma.practice

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/services")
class PracticeController(
    val practiceService: PracticeService
){

    @PostMapping
    fun saveService(@Valid practiceRequest: PracticeRequest): ResponseEntity<PracticeResponse> {
        return practiceService.saveService(practiceRequest)
    }
    @GetMapping
    fun getAllServices(): ResponseEntity<List<PracticeResponse>>{
        return ResponseEntity.ok(practiceService.getAllServices())
    }

    @GetMapping("/{sangomaId}")
    fun getAllServicesBySangomaId(@PathVariable sangomaId: Long): List<PracticeResponse>{
        return practiceService.findBySangomaId(sangomaId)
    }
//    @GetMapping("/categories")
//    fun getAllServiceCategories(): ResponseEntity<List<SangomaServiceCategory>>{
//        return sangomaServiceService.getAllServiceCategories()
//    }
}