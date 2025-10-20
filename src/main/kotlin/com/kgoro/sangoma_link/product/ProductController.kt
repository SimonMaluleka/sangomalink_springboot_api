package com.kgoro.sangoma_link.product
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/products")
class ProductController (
    val productService: ProductService,
){

    @GetMapping
    fun allProducts(): ResponseEntity<List<ProductResponse>> {
        print("\n get products controller method invoked")
        val products = productService.allProducts()
        print("\n Products: $products")
        return ResponseEntity.ok(productService.allProducts())
    }

    @PostMapping
    fun createProduct(@RequestBody @Valid request: ProductRequest): ResponseEntity<Int> {
        return ResponseEntity.ok(productService.createProduct(request))
    }

//    @GetMapping("/product/{productId}/image")
//    fun getImageByProductId(@PathVariable productId: Int): ResponseEntity<List<Byte>> {
//        val product = productService.getProductById(productId)
//        val imageFile: List<Byte> = product.imageUrls[0]
//        return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(imageFile)
//    }

//    @PostMapping("/purchase")
//    fun purchaseProducts(@RequestBody request: List<ProductPurchaseRequest>): ResponseEntity<List<ProductPurchaseResponse>> {
//        return ResponseEntity.ok(productService.purchaseProducts(request))
//    }
//
//    @GetMapping("/product/{id}")
//    fun productExistsById(@PathVariable id: String): Boolean {
//        return productService.productExistsById()
//    }
    @GetMapping("/{product-id}")
    fun getProductById(@PathVariable("product-id") productId: Int): ResponseEntity<ProductResponse> {
        return ResponseEntity.ok(productService.getProductById(productId))
    }
//
//    @PutMapping("/{id}")
//    fun updateProduct(@PathVariable id: String, @RequestBody @Valid product: Product): Product {
//        return productService.updateProduct(id, product)
//    }
//
//    @DeleteMapping("/{id}")
//    fun deleteProduct(@PathVariable id: String): Unit {
//        productService.deleteProduct(id)
//    }
}
    
