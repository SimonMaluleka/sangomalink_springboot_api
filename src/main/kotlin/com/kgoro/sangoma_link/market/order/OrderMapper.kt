
import com.kgoro.sangoma_link.market.order.Order
import org.springframework.stereotype.Service


@Service
class OrderMapper {
    fun toOrder(request: OrderRequest): Order {
        return Order(
            id = request.id,
            customerId = request.customerId,
            subtotalAmount = request.subtotalAmount,
            taxAmount = request.taxAmount,
            shippingAmount = request.shippingAmount,
            totalAmount = request.totalAmount,
            currency = request.currency,
            orderStatus = request.orderStatus,
            paymentStatus = request.paymentStatus,
            shippingAddressId = request.shippingAddressId,
            billingAddressId = request.billingAddressId,
            customerNotes = request.customerNotes,
            createdAt = request.createdAt,
            updatedAt = request.updatedAt,
            cancelledAt = request.cancelledAt
        )
    }
}
