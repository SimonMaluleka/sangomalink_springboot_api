import com.kgoro.sangoma_link.product.Product
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductRepository: JpaRepository<Product, Long > {
    abstract fun findAllProducts(): List<Product>
    abstract fun findAllById(productIds: List<Long>): MutableList<Product>
    abstract fun findProductById(productId: Long): Optional<Product>
    abstract fun purchaseProducts(products: List<Product>): List<Product>
}