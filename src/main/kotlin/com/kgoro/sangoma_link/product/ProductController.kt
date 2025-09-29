import com.kgoro.sangoma_link.product.ProductResponse
import jakarta.validation.Valid
import lombok.RequiredArgsConstructor
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
class ProductController (val productService: ProductService){
    @GetMapping
    fun allProducts(): ResponseEntity<List<ProductResponse>> {
        return ResponseEntity.ok(productService.allProducts)
    }

    @PostMapping
    fun createProduct(@RequestBody @Valid request: ProductRequest): ResponseEntity<Long> {
        return ResponseEntity.ok(productService.createProduct(request))
    }

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
    fun getProductById(@PathVariable("product-id") productId: Long): ResponseEntity<ProductResponse> {
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
    
