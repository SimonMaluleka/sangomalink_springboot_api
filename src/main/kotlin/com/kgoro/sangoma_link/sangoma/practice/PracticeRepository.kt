package com.kgoro.sangoma_link.sangoma.practice

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface PracticeRepository: JpaRepository<Practice, Long> {
    abstract fun save(practice: Practice): PracticeResponse
//    abstract fun findBySangomaId(sangomaId: Long)
//    abstract fun getAllServices(): List<SangomaService>
    //abstract fun getAllServiceCategories(): List<SangomaServiceCategory>
    abstract fun findServiceByName(serviceName: String): Optional<Practice>
    abstract fun findServiceById(serviceId: Long): Optional<Practice>
    fun findServiceBySangomaId(sangomaId: Long): List<Practice>
}