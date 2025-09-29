package com.kgoro.sangoma_link.service.category

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class Service (
    val repository: Repository
){
    fun getAllServiceCategories(): ResponseEntity<List<Category>> {
        return ResponseEntity.ok(repository.findAll())
    }
}