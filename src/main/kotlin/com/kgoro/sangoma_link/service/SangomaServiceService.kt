package com.kgoro.sangoma_link.service

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

import java.util.*

@Service
class SangomaServiceService(
    val sangomaServiceRepository: SangomaServiceRepository
) {
    fun findBySangomaId(sangomaId: Long): List<SangomaServiceResponse>{
        val services = sangomaServiceRepository.findServiceBySangomaId(sangomaId)
            .map { service -> SangomaServiceResponse(
                id = service.id,
                sangomaId = service.sangoma.id,
                categoryId = service.category!!.id,
                name = service.name,
                description = service.description,
                durationMinutes = service.durationMinutes,
                price = service.price,
                currency = service.currency,
                serviceType = service.serviceType,
                isActive = service.isActive,
                createdAt =  service.createdAt,
                updatedAt = service.updatedAt
            ) }
        return services
    }
    fun saveService(sangomaServiceRequest: SangomaServiceRequest): ResponseEntity<SangomaServiceResponse>{
        val service = sangomaServiceRepository.save(SangomaService(
            id = 0,
            sangoma = sangomaServiceRequest.sangoma,
            category = sangomaServiceRequest.category,
            name = sangomaServiceRequest.name,
            description = sangomaServiceRequest.description,
            durationMinutes = sangomaServiceRequest.durationMinutes,
            price = sangomaServiceRequest.price,
            currency = sangomaServiceRequest.currency,
            serviceType = sangomaServiceRequest.serviceType,
            isActive = false ,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        ))

        val response = SangomaServiceResponse(
            id = service.id,
            sangomaId = service.sangomaId,
            categoryId = service.categoryId,
            name = service.name,
            description = service.description,
            durationMinutes = service.durationMinutes,
            price = service.price,
            currency = service.currency,
            serviceType = service.serviceType,
            isActive = service.isActive,
            createdAt =  service.createdAt,
            updatedAt = service.updatedAt
        )

        return ResponseEntity.ok(response)
    }
    fun getAllServices(): List<SangomaServiceResponse> {
        val services = sangomaServiceRepository.findAll()
            .map { service -> SangomaServiceResponse(
                id = service.id,
                sangomaId = service.sangoma.id,
                categoryId = service.category!!.id,
                name = service.name,
                description = service.description,
                durationMinutes = service.durationMinutes,
                price = service.price,
                currency = service.currency,
                serviceType = service.serviceType,
                isActive = service.isActive,
                createdAt =  service.createdAt,
                updatedAt = service.updatedAt
            ) }
        return services
    }
//    fun getAllServiceCategories(): ResponseEntity<List<SangomaServiceCategory>>{
//        return ResponseEntity.ok(sangomaServiceRepository.getAllServiceCategories())
//    }
//    fun findServiceByName(serviceName: String): Optional<>
//    fun findServiceByID(serviceId: UUID): Optional<>
}