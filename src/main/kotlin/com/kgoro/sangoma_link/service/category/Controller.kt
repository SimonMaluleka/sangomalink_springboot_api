package com.kgoro.sangoma_link.service.category

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/service-category")
class Controller (
    val service: Service
){
    @GetMapping
    fun getAllServiceCategories(): ResponseEntity<List<Category>>{
        return service.getAllServiceCategories()
    }
}