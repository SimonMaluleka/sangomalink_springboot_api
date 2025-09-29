package com.kgoro.sangoma_link.cart

import com.kgoro.sangoma_link.product.Product
import com.kgoro.sangoma_link.product.ProductVariation
import com.kgoro.sangoma_link.user.User
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "carts")
data class Cart(
    @Id val id: Long,
    @OneToOne()
    @JoinColumn(name = "user_id")
    var user: User?,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
)

@Entity
@Table(name = "cart_items")
data class CartItem(
    @Id val id: Long,
    @ManyToOne @JoinColumn(name = "cart_id", nullable = false)
    val cart: Cart,
    @ManyToOne @JoinColumn(name = "product_id", nullable = false)
    val product: Product,
    @ManyToOne @JoinColumn(name = "variation_id")
    val variation: ProductVariation? = null,
    @Column(nullable = false)
    val quantity: Long,
    @Column(nullable = false, precision = 10, scale = 2)
    val unitPrice: BigDecimal,
    val addedAt: LocalDateTime = LocalDateTime.now()
)
