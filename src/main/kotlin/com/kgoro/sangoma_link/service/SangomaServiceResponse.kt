package com.kgoro.sangoma_link.service

import com.kgoro.sangoma_link.sangoma_profile.SangomaProfile
import com.kgoro.sangoma_link.service.category.Category
import com.kgoro.sangoma_link.user.enums.ServiceType
import java.math.BigDecimal

class SangomaServiceResponse (
    val sangoma: SangomaProfile,
    val category: Category? = null,
    val name: String,
    val description: String? = null,
    val durationMinutes: Long,
    val price: BigDecimal,
    val currency: String = "ZAR",
    var serviceType: ServiceType
)