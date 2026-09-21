
import com.kgoro.sangoma_link.market.order.Order
import com.kgoro.sangoma_link.market.product.ProductRepository
import com.kgoro.sangoma_link.user.UserRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class OrderService(
    val orderRepository: OrderRepository,
    val userRepository: UserRepository,
    val productRepository: ProductRepository,
    val mapper: OrderMapper
) {
    fun createOrder(request: OrderRequest): Long {
        println("create order called")
        //check the customer exists --> customer microservice
        val customer = userRepository.findById(request.customerId)
//            .orElseThrow {
//                CustomerNotFoundException("Cannot create order::No Customer exists with the provided ID: ${request.customerId}")
//            }

        //purchase the products --> product microservice
        //val products = productRepository.purchaseProducts(request.products)

        // persist the new order object
        val order = orderRepository.save(mapper.toOrder(request))
        println(order)

        // persist the order lines
//        for (purchaseRequest: OrderPurchaseRequest in request.products){
//
//        }
        // orderRepository.save(request)

        // start payment process using the payment microservice

        //send order confirmation --> notification micro-service (kafka)


        return order.id
    }

    fun getAllOrders(): List<Order>? {
        return orderRepository.findAll()
    }

}


