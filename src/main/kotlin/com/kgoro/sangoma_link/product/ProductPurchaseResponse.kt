package com.kgoro.sangoma_link.product
import java.math.BigDecimal


data class ProductPurchaseResponse(
    val productId: Int,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val quantity: Double
)