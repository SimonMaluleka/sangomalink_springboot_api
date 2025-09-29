
package com.kgoro.sangoma_link.service

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/sangoma")
class SangomaServiceController(
    val sangomaServiceService: SangomaServiceService
){

    @PostMapping
    fun saveService(@Valid sangomaServiceRequest: SangomaServiceRequest): ResponseEntity<SangomaServiceResponse> {
        return sangomaServiceService.saveService(sangomaServiceRequest)
    }
//    @GetMapping
//    fun getAllServices(): ResponseEntity<List<SangomaService>>{
//        return sangomaServiceService.getAllServices()
//    }

//    @GetMapping("/categories")
//    fun getAllServiceCategories(): ResponseEntity<List<SangomaServiceCategory>>{
//        return sangomaServiceService.getAllServiceCategories()
//    }
}