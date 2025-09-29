
import jakarta.validation.constraints.NotNull
import java.util.*

data class ProductPurchaseRequest (
    @NotNull(message="Product is mandatory")
    val id: Long,
    @NotNull(message="Product is mandatory")
    val quantity: Double
)
