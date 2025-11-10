package com.kgoro.sangoma_link.service

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SangomaServiceRepository: JpaRepository<SangomaService, Long> {
    abstract fun save(service: SangomaService): SangomaServiceResponse
//    abstract fun findBySangomaId(sangomaId: Long)
//    abstract fun getAllServices(): List<SangomaService>
    //abstract fun getAllServiceCategories(): List<SangomaServiceCategory>
    abstract fun findServiceByName(serviceName: String): Optional<SangomaService>
    abstract fun findServiceById(serviceId: Long): Optional<SangomaService>
    fun findServiceBySangomaId(sangomaId: Long): List<SangomaService>
}