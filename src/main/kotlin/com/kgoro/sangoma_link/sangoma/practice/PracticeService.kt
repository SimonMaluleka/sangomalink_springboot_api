package com.kgoro.sangoma_link.sangoma.practice

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class PracticeService(
    val practiceRepository: PracticeRepository
) {
    fun findBySangomaId(sangomaId: Long): List<PracticeResponse>{
        val services = practiceRepository.findServiceBySangomaId(sangomaId)
            .map { service -> PracticeResponse(
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
    fun saveService(practiceRequest: PracticeRequest): ResponseEntity<PracticeResponse>{
        val practice = practiceRepository.save(Practice(
            id = 0,
            sangoma = practiceRequest.sangoma,
            category = practiceRequest.category,
            name = practiceRequest.name,
            description = practiceRequest.description,
            durationMinutes = practiceRequest.durationMinutes,
            price = practiceRequest.price,
            currency = practiceRequest.currency,
            serviceType = practiceRequest.serviceType,
            isActive = false ,
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        ))

        val response = PracticeResponse(
            id = practice.id,
            sangomaId = practice.sangomaId,
            categoryId = practice.categoryId,
            name = practice.name,
            description = practice.description,
            durationMinutes = practice.durationMinutes,
            price = practice.price,
            currency = practice.currency,
            serviceType = practice.serviceType,
            isActive = practice.isActive,
            createdAt =  practice.createdAt,
            updatedAt = practice.updatedAt
        )

        return ResponseEntity.ok(response)
    }
    fun getAllServices(): List<PracticeResponse> {
        val services = practiceRepository.findAll()
            .map { service -> PracticeResponse(
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