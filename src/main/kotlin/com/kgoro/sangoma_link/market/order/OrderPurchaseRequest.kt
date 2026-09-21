import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

class OrderPurchaseRequest (
    @NotNull(message = "Product is mandatory")
    val productId: Long,
    @Positive(message = "Quantity is mandatory")
    val quantity: Double
)
