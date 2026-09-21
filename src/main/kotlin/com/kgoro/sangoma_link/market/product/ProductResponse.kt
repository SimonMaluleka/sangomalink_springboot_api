package com.kgoro.sangoma_link.market.product
import com.kgoro.sangoma_link.user.enums.WeightUnit
import java.math.BigDecimal


class ProductResponse (
    val id: Int,
    val sangomaName: String,
    val category: String,
    val name: String,
    val description: String?,
    val ingredients: String?,
    val usageInstructions: String?,
    val contraindications: String?,
    val price: BigDecimal,
    val currency: String,
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


