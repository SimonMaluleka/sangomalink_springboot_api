package com.kgoro.sangoma_link.product

import com.kgoro.sangoma_link.sangoma_profile.SangomaProfile
import com.kgoro.sangoma_link.user.enums.VariationType
import com.kgoro.sangoma_link.user.enums.WeightUnit
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*
import kotlin.properties.Delegates

@Entity
@Table(name = "products")
data class Product(
    @Id val id: Int,
    @ManyToOne @JoinColumn(name = "sangoma_id", nullable = false)
    val sangoma: SangomaProfile,
    @ManyToOne @JoinColumn(name = "category_id")
    val category: ProductCategory,
    @Column(nullable = false) val name: String,
    val description: String,
    val ingredients: String? = null,
    val usageInstructions: String? = null,
    val contraindications: String? = null,
    @Column(nullable = false, precision = 10, scale = 2)
    val price: BigDecimal,
    @Column(length = 3) val currency: String = "ZAR",
    var stockQuantity: Long = 0,
    val minOrderQuantity: Long = 1,
    val maxOrderQuantity: Long? = null,
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    val weightUnit: WeightUnit,
    @Column(precision = 10, scale = 2)
    val weightValue: BigDecimal,
    @Column(columnDefinition = "jsonb")
    val imageUrls: String = "[]",
    @Column(precision = 3, scale = 2)
    val averageRating: BigDecimal = BigDecimal("0.00"),
    val totalReviews: Long = 0,
    val isApproved: Boolean = false,
    val isAvailable: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
){
    class BuildProduct {
        private var id by Delegates.notNull<Long>()
        private lateinit var sangoma: SangomaProfile
        private lateinit var category: ProductCategory
        private lateinit var name: String
        private lateinit var description: String
        private lateinit var ingredients: String
        private lateinit var usageInstructions: String
        private lateinit var contraindications: String
        private lateinit var price:  BigDecimal
        private lateinit var currency: String
        private var stockQuantity by Delegates.notNull<Long>()
        private var minOrderQuantity by Delegates.notNull<Long>()
        private var maxOrderQuantity: Long? = null
        private lateinit var weightUnit: WeightUnit
        private lateinit var weightValue: BigDecimal
        private lateinit var imageUrls: String
        private lateinit var averageRating: BigDecimal
        private var totalReviews by Delegates.notNull<Long>()
        private var isApproved by Delegates.notNull<Boolean>()
        private var isAvailable by Delegates.notNull<Boolean>()

        fun setId(id: Long)= apply { this.id = id }
        fun setSangoma(sangoma: SangomaProfile)= apply { this.sangoma = sangoma}
        fun setCategory(category: ProductCategory)= apply { this.category = category}
        fun setName(name: String)= apply { this.name = name }
        fun setDescription(description: String)= apply { this.description = description }
        fun setIngredients(ingredients: String)= apply { this.ingredients = ingredients }
        fun setUsageInstructions(usageInstructions: String)= apply { this.usageInstructions = usageInstructions}
        fun setContraindications(contraindications: String)= apply { this.contraindications = contraindications}
        fun setPrice(price:  BigDecimal)= apply { this.price = price }
        fun setCurrency(currency: String)= apply { this.currency = currency }
        fun setStockQuantity(stockQuantity: Long)= apply { this.stockQuantity = stockQuantity}
        fun setMinOrderQuantity(minOrderQuantity: Long)= apply { this.minOrderQuantity= minOrderQuantity}
        fun setMaxOrderQuantity(maxOrderQuantity: Long?)= apply { this.maxOrderQuantity= maxOrderQuantity}
        fun setWeightUnit(weightUnit: WeightUnit)= apply { this.weightUnit= weightUnit}
        fun setWeightValue(weightValue: BigDecimal)= apply { this.weightValue = weightValue}
        fun setImageUrls(imageUrls: String)= apply { this.imageUrls= imageUrls}
        fun setAverageRating(averageRating: BigDecimal)= apply { this.averageRating= averageRating}
        fun setTotalReviews(totalReviews: Long)= apply { this.totalReviews = totalReviews}
        fun setIsApproved(isApproved: Boolean)= apply { this.isApproved = isApproved}
        fun setIsAvailable(isAvailable: Boolean)= apply { this.isAvailable = isAvailable}

        fun build(): Product {
            return Product(
                id = 0,
                sangoma,
                category,
                name,
                description,
                ingredients,
                usageInstructions ,
                contraindications ,
                price,
                currency,
                stockQuantity,
                minOrderQuantity,
                maxOrderQuantity,
                weightUnit,
                weightValue,
                imageUrls,
                averageRating,
                totalReviews,
                isApproved,
                isAvailable
            )
        }
    }
}


@Entity
@Table(name = "product_categories")
data class ProductCategory(
    @Id val id: Long,
    @Column(nullable = false) val name: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val displayOrder: Long = 0,
    val isActive: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

@Entity
@Table(name = "product_variations")
data class ProductVariation(
    @Id val id: Long,
    @ManyToOne @JoinColumn(name = "product_id", nullable = false)
    val product: Product,
    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    val variationType: VariationType,
    @Column(nullable = false)
    val variationValue: String,
    @Column(precision = 10, scale = 2)
    val priceAdjustment: BigDecimal = BigDecimal.ZERO,
    val stockQuantity: Long = 0,
    val sku: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

@Entity
@Table(name = "product_reviews")
data class ProductReview(
    @Id val id: Long,
    @ManyToOne @JoinColumn(name = "product_id", nullable = false)
    val product: Product,
    val customerId: Long,
    @Column(nullable = false)
    val rating: Long,
    val comment: String? = null,
    val sellerResponse: String? = null,
    val isVerifiedPurchase: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

