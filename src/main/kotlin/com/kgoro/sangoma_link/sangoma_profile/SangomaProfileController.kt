package com.kgoro.sangoma_link.sangoma_profile

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/healers")
class SangomaProfileController(
    val sangomaProfileService: SangomaProfileService,
    val sangomaProfileMapper: SangomaProfileMapper
) {
    @GetMapping("/featured")
    fun fetchFeaturedSangomaProfiles(): ResponseEntity<List<SangomaProfileResponse>>{
        val healers =  sangomaProfileService.findByIsFeaturedTrue()

        val response = healers.stream()
            .map { sangoma -> sangomaProfileMapper.toSangomaProfileResponse(sangoma) }
            .toList()

        return ResponseEntity.ok(response)

    }

    @GetMapping
    fun findAll(): ResponseEntity<List<SangomaProfileResponse>>{
        val healers =  sangomaProfileService.findAll()

        val response = healers.stream()
            .map { sangoma -> sangomaProfileMapper.toSangomaProfileResponse(sangoma) }
            .toList()

        return ResponseEntity.ok(response)

    }
}