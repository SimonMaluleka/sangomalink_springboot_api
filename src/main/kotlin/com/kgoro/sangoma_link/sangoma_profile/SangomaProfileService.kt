package com.kgoro.sangoma_link.sangoma_profile

import com.kgoro.sangoma_link.user.User
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class SangomaProfileService (
    val sangomaProfileRepository: SangomaProfileRepository
){
    fun createSangomaProfile(savedUser: User, sangomaSpecificData: SangomaSpecificData) {}

    fun findByIsFeaturedTrue(): List<SangomaProfile> {
        return sangomaProfileRepository.findByIsFeaturedTrue()
    }

    fun findAll(): List<SangomaProfile> {
        return sangomaProfileRepository.findAll();
    }
}