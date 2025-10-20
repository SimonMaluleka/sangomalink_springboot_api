package com.kgoro.sangoma_link.product

import com.kgoro.sangoma_link.user.enums.WeightUnit
import org.apache.catalina.valves.rewrite.InternalRewriteMap
import org.springframework.stereotype.Service

@Service
class ProductMapper {
    fun toProduct(request: ProductRequest): Product {
        return Product.BuildProduct()
            .setSangoma(request.sangoma)
            .setCategory(request.category)
            .setName(request.name)
            .setDescription(request.description)
            .setIngredients(request.ingredients)
            .setUsageInstructions(request.usageInstructions)
            .setContraindications(request.contraindications)
            .setPrice(request.price)
            .setCurrency(request.currency)
            .setStockQuantity(request.stockQuantity)
            .setMinOrderQuantity(request.minOrderQuantity)
            .setMaxOrderQuantity(request.maxOrderQuantity)
            .setWeightUnit(request.weightUnit)
            .setWeightValue(request.weightValue)
            .setImageUrls(request.imageUrls)
            .setAverageRating(request.averageRating)
            .setTotalReviews(request.totalReviews)
            .setIsApproved(request.isApproved)
            .setIsAvailable(request.isAvailable)
            .build()
    }

    fun toProductResponse(product: Product): ProductResponse {
        return ProductResponse(
            product.id,
            product.sangoma.user.firstName +" " + product.sangoma.user.lastName,
            product.category.name,
            product.name,
            product.description,
            product.ingredients,
            product.usageInstructions,
            product.contraindications,
            product.price,
            product.currency,
            product.stockQuantity,
            product.minOrderQuantity,
            product.maxOrderQuantity,
            product.weightUnit,
            product.weightValue,
            product.imageUrls,
            product.averageRating,
            product.totalReviews,
            product.isApproved,
            product.isAvailable,
        )
    }

    fun toProductPurchaseResponse(product: Product, quantity: Double): ProductPurchaseResponse {
        return ProductPurchaseResponse(
            product.id,
            product.name,
            product.description,
            product.price,
            quantity
        )
    }
}
