package com.kgoro.sangoma_link.service

import com.kgoro.sangoma_link.sangoma_profile.SangomaProfile
import com.kgoro.sangoma_link.service.category.Category
import com.kgoro.sangoma_link.user.enums.ServiceType
import java.math.BigDecimal
import java.time.LocalDateTime

class SangomaServiceResponse (
    val id: Long,
    val sangomaId: Long,
    val categoryId: Long,
    val name: String,
    val description: String? = null,
    val durationMinutes: Long,
    val price: BigDecimal,
    val currency: String = "ZAR",
    var serviceType: ServiceType,
    val isActive: Boolean,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?
)
//class SangomaServiceResponse (
//    val sangoma: SangomaProfile,
//    val category: Category? = null,
//    val name: String,
//    val description: String? = null,
//    val durationMinutes: Long,
//    val price: BigDecimal,
//    val currency: String = "ZAR",
//    var serviceType: ServiceType
//)