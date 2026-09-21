package com.kgoro.sangoma_link.market.order

import com.kgoro.sangoma_link.market.product.Product
import com.kgoro.sangoma_link.market.product.ProductVariation
import com.kgoro.sangoma_link.user.enums.OrderStatus
import com.kgoro.sangoma_link.user.enums.PaymentStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.properties.Delegates

@Entity
@Table(name = "orders")
data class Order(
    @Id val id: Long,
    val customerId: Long,
    @Column(precision = 10, scale = 2)
    val subtotalAmount: BigDecimal,
    @Column(precision = 10, scale = 2)
    val taxAmount: BigDecimal = BigDecimal.ZERO,
    @Column(precision = 10, scale = 2)
    val shippingAmount: BigDecimal = BigDecimal.ZERO,
    @Column(precision = 10, scale = 2)
    val totalAmount: BigDecimal,
    @Column(length = 3)
    val currency: String = "ZAR",
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val orderStatus: OrderStatus = OrderStatus.PENDING,
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING,
    val shippingAddressId: Long? = null,
    val billingAddressId: Long? = null,
    val customerNotes: String? = null,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val cancelledAt: LocalDateTime? = null
) {
    class BuildOrder {
        private var id by Delegates.notNull<Long>()
        private var customerId by Delegates.notNull<Long>()
        private lateinit var subtotalAmount: BigDecimal
        private lateinit var taxAmount: BigDecimal
        private lateinit var shippingAmount: BigDecimal
        private lateinit var totalAmount: BigDecimal
        private lateinit var currency: String
        private lateinit var orderStatus: OrderStatus
        private lateinit var paymentStatus: PaymentStatus
        private var shippingAddressId by Delegates.notNull<Long>()
        private var billingAddressId by Delegates.notNull<Long>()
        private lateinit var customerNotes: String
        private lateinit var createdAt: LocalDateTime
        private lateinit var updatedAt: LocalDateTime
        private lateinit var cancelledAt: LocalDateTime

        fun setId(id: Long)= apply { this.id = id }
        fun setCustomerId(customerId: Long)= apply { this.customerId = customerId }
        fun setSubtotalAmount(subtotalAmount: BigDecimal) = apply { this.subtotalAmount = subtotalAmount }
        fun setTaxAmount(taxAmount: BigDecimal) = apply { this.taxAmount = taxAmount }
        fun setShippingAmount(shippingAmount: BigDecimal) = apply { this.shippingAmount = shippingAmount }
        fun setTotalAmount(totalAmount: BigDecimal) = apply { this.totalAmount = totalAmount }
        fun setCurrency(currency: String) = apply { this.currency = currency }
        fun setOrderStatus(orderStatus: OrderStatus) = apply { this.orderStatus = orderStatus }
        fun setPaymentStatus(paymentStatus: PaymentStatus) = apply { this.paymentStatus = paymentStatus }
        fun setShippingAddressId(shippingAddressId: Long)= apply { this.shippingAddressId = shippingAddressId }
        fun setBillingAddressId(billingAddressId: Long)= apply { this.billingAddressId = billingAddressId }
        fun setCreatedAt(createdAt: LocalDateTime) = apply { this.createdAt = createdAt}
        fun setUpdatedAt(updatedAt: LocalDateTime) = apply { this.updatedAt = updatedAt}
        fun setCancelledAt(cancelledAt: LocalDateTime) = apply { this.cancelledAt = cancelledAt}

        fun build(): Order{
            return Order(
                id,
                customerId,
                subtotalAmount,
                taxAmount,
                shippingAmount,
                totalAmount,
                currency,
                orderStatus,
                paymentStatus,
                shippingAddressId,
                billingAddressId,
                customerNotes,
                createdAt,
                updatedAt,
                cancelledAt
            )
        }
    }
}

@Entity
@Table(name = "order_items")
data class OrderItem(
    @Id val id: Long,
    @ManyToOne @JoinColumn(name = "order_id", nullable = false)
    val order: Order,
    @ManyToOne @JoinColumn(name = "product_id", nullable = false)
    val product: Product,
    @ManyToOne @JoinColumn(name = "variation_id")
    val variation: ProductVariation? = null,
    @Column(nullable = false)
    val productName: String,
    val productDescription: String? = null,
    @Column(nullable = false, precision = 10, scale = 2)
    val unitPrice: BigDecimal,
    @Column(nullable = false)
    val quantity: Long,
    @Column(nullable = false, precision = 10, scale = 2)
    val totalPrice: BigDecimal,
    val createdAt: LocalDateTime = LocalDateTime.now()
)
