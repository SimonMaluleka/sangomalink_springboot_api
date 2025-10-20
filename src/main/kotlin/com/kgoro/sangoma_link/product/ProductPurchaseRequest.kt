package com.kgoro.sangoma_link.product

import jakarta.validation.constraints.NotNull

data class ProductPurchaseRequest (
    @NotNull(message="Product is mandatory")
    val id: Int,
    @NotNull(message="Product is mandatory")
    val quantity: Double
)
