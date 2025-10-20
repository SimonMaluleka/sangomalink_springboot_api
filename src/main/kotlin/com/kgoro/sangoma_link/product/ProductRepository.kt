package com.kgoro.sangoma_link.product

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository: JpaRepository<Product, Int> {
    //abstract fun findAllProducts(): List<Product>
//    abstract fun findAllById(productIds: List<Long>): MutableList<Product>
    abstract fun findProductById(productId: Int): Optional<Product>
    //abstract fun purchaseProducts(products: List<Product>): List<Product>
}