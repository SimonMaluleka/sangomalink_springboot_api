
package com.kgoro.sangoma_link.product


import com.kgoro.sangoma_link.sangoma_profile.SangomaProfile

import com.kgoro.sangoma_link.user.enums.WeightUnit
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal


class ProductRequest (
    val sangoma: SangomaProfile,
    @NotNull(message = "Product category is required")
    val category: ProductCategory,
    @NotNull(message = "Product description is required")
    val name: String,
    val  description: String,
    val ingredients: String,
    val usageInstructions: String,
    val contraindications: String,
    @Positive(message = "Price quantity should be positive")
    val price: BigDecimal,
    val currency: String,
    @Positive(message = "Available quantity should be positive")
    val stockQuantity: Long,
    val minOrderQuantity: Long,
    val maxOrderQuantity: Long?,
    val weightUnit: WeightUnit,
    val weightValue: BigDecimal,
    val imageUrls: String,
    val averageRating: BigDecimal,
    val totalReviews: Long,
    val isApproved: Boolean,
    val isAvailable: Boolean
)
