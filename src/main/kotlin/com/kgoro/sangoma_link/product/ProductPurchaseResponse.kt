
import java.math.BigDecimal


data class ProductPurchaseResponse(
    val productId: Long,
    val name: String,
    val description: String,
    val price: BigDecimal,
    val quantity: Double
)