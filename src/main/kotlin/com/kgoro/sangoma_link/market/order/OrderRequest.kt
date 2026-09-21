import com.kgoro.sangoma_link.user.enums.OrderStatus
import com.kgoro.sangoma_link.user.enums.PaymentStatus
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDateTime

class OrderRequest (
    val id: Long,
    @NotNull(message = "Customer should be present")
    @NotEmpty(message = "Customer should be present")
    @NotBlank(message = "Customer should be present")
    val customerId: Long,
    val subtotalAmount: BigDecimal,
    val taxAmount: BigDecimal = BigDecimal.ZERO,
    val shippingAmount: BigDecimal = BigDecimal.ZERO,
    val totalAmount: BigDecimal,
    val currency: String = "ZAR",
    val orderStatus: OrderStatus = OrderStatus.PENDING,
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val shippingAddressId: Long? = null,
    val billingAddressId: Long? = null,
    val customerNotes: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val cancelledAt: LocalDateTime? = null,

//    @NotEmpty(message = "You should at least purchase one product")
//    val products: List<OrderPurchaseRequest>
)
