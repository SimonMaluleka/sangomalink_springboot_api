
package com.kgoro.sangoma_link.service

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/services")
class SangomaServiceController(
    val sangomaServiceService: SangomaServiceService
){

    @PostMapping
    fun saveService(@Valid sangomaServiceRequest: SangomaServiceRequest): ResponseEntity<SangomaServiceResponse> {
        return sangomaServiceService.saveService(sangomaServiceRequest)
    }
    @GetMapping
    fun getAllServices(): ResponseEntity<List<SangomaServiceResponse>>{
        return ResponseEntity.ok(sangomaServiceService.getAllServices())
    }

    @GetMapping("/{sangomaId}")
    fun getAllServicesBySangomaId(@PathVariable sangomaId: Long): List<SangomaServiceResponse>{
        return sangomaServiceService.findBySangomaId(sangomaId)
    }
//    @GetMapping("/categories")
//    fun getAllServiceCategories(): ResponseEntity<List<SangomaServiceCategory>>{
//        return sangomaServiceService.getAllServiceCategories()
//    }
}