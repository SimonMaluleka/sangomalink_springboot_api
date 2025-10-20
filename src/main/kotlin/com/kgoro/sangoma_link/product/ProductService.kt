package com.kgoro.sangoma_link.product

import jakarta.persistence.EntityNotFoundException
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class ProductService(
    val productRepository: ProductRepository,
    val mapper: ProductMapper
) {
    fun allProducts(): List<ProductResponse> {
        return productRepository.findAll()
            .stream()
            .map(mapper::toProductResponse)
            .collect(Collectors.toList())
    }

    fun createProduct(request: ProductRequest): Int {
        val product = mapper.toProduct(request)
        return productRepository.save(product).id
    }


    fun purchaseProducts(request: List<ProductPurchaseRequest>): List<ProductPurchaseResponse>{
        val productIds = request.stream().map(ProductPurchaseRequest::id).toList()
        val storedProducts = productRepository.findAllById(productIds)

        // check products are available
        if(productIds.size != storedProducts.size){
            throw EntityNotFoundException("Some items in your cart are not available")
        }

        val storedRequest = request
            .stream()
            .sorted(Comparator.comparing(ProductPurchaseRequest::id))
            .toList()

        val purchasedProducts = ArrayList<ProductPurchaseResponse>()

        for(product in storedProducts){
            for (productRequest in storedRequest){
                if (product.stockQuantity < productRequest.quantity){
                    throw EntityNotFoundException("Product with ID:: ${product.id} is out of stock")
                }

                val newAvailableQuantity = product.stockQuantity - productRequest.quantity

                product.stockQuantity = newAvailableQuantity.toLong()

                productRepository.save(product)

                purchasedProducts.add(mapper.toProductPurchaseResponse(product, productRequest.quantity))
            }
        }
        return purchasedProducts
    }

    fun getProductById(productId: Int): ProductResponse {
        val product = productRepository.findProductById(productId)
        return product
            .map(mapper::toProductResponse)
            .orElseThrow {
                EntityNotFoundException("Product not found with ID:: $productId")
            }

    }

//    fun productExistsById(productId: Long): ResponseEntity<Boolean> {
//        return ResponseEntity.ok(productRepository.findById(productId))
//            .map()
//    }
//
    

}