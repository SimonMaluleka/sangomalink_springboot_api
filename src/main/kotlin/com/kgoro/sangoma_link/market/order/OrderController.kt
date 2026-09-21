import com.kgoro.sangoma_link.market.order.Order
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("orders")
class OrderController (
    val orderService: OrderService
){
    @PostMapping
    fun createOrder(@RequestBody @Valid request: OrderRequest): ResponseEntity<Long> {
        println(request.id)
        return ResponseEntity.ok(orderService.createOrder(request))
    }

    @GetMapping
    fun getAllOrders(): ResponseEntity<List<Order>>{
        return ResponseEntity.ok(orderService.getAllOrders())
    }
}