package com.kgoro.sangoma_link.sangoma.practice

import com.kgoro.sangoma_link.sangoma.profile.Profile
import com.kgoro.sangoma_link.sangoma.practice.category.Category
import com.kgoro.sangoma_link.user.enums.ServiceType
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime
import kotlin.properties.Delegates

@Entity
@Table(name = "services")
data class Practice(
    @Id
    val id: Long,
    @ManyToOne
    @JoinColumn(name = "sangoma_id", nullable = false)
    val sangoma: Profile,
    @ManyToOne
    @JoinColumn(name = "category_id")
    val category: Category? = null,
    @Column(nullable = false)
    val name: String,
    val description: String? = null,
    @Column(nullable = false)
    val durationMinutes: Long,
    @Column(nullable = false, precision = 10, scale = 2)
    val price: BigDecimal,
    @Column(length = 3)
    val currency: String = "ZAR",
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var serviceType: ServiceType,
    val isActive: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    companion object {
        class BuildSangomaService {
            private var id by Delegates.notNull<Long>()
            private lateinit var sangoma: Profile
            private lateinit var category: Category
            private lateinit var name: String
            private lateinit var description: String
            private var durationMinutes by Delegates.notNull<Long>()
            private lateinit var price: BigDecimal
            private lateinit var currency: String
            private lateinit var serviceType: ServiceType
            private var isActive by Delegates.notNull<Boolean>()
            private lateinit var createdAt: LocalDateTime
            private lateinit var updatedAt: LocalDateTime

            fun setId(id: Long) = apply { this.id = id }
            fun setSangoma(sangoma: Profile) = apply { this.sangoma = sangoma }
            fun setCategory(category: Category) = apply { this.category = category }
            fun setName(name: String) = apply { this.name = name }
            fun setDescription(description: String) = apply { this.description = description }
            fun setDuration(durationMinutes: Long) = apply { this.durationMinutes = durationMinutes }
            fun setPrice(price: BigDecimal) = apply { this.price = price }
            fun setCurrency(currency: String) = apply { this.currency = currency }
            fun setServiceType(serviceType: ServiceType) = apply { this.serviceType = serviceType }
            fun setIsActive(isActive: Boolean) = apply { this.isActive = isActive }
            fun setCreatedAt(createdAt: LocalDateTime) = apply { this.createdAt = createdAt }
            fun setUpdateAt(updatedAt: LocalDateTime) = apply { this.updatedAt = updatedAt }

            fun build(): Practice {
                return Practice(
                    id = 0,
                    sangoma,
                    category,
                    name,
                    description,
                    durationMinutes,
                    price,
                    currency,
                    serviceType,
                    isActive,
                    createdAt,
                    updatedAt
                )
            }
        }
    }
}