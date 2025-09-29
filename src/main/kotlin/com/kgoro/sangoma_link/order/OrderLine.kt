package com.akanithemba.ecommerce.order

import com.kgoro.sangoma_link.order.Order
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
data class OrderLine (
    @Id
    @GeneratedValue
    val id: Long,
    @ManyToOne
    @JoinColumn(name = "order_id")
    val order: Order,
    val productId: Long,
    val quantity: Double
)