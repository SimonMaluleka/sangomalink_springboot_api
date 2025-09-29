import com.kgoro.sangoma_link.order.Order
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OrderRepository: JpaRepository<Order, Long>
