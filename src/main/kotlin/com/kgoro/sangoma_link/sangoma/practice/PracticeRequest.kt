package com.kgoro.sangoma_link.sangoma.practice

import com.kgoro.sangoma_link.sangoma.profile.Profile
import com.kgoro.sangoma_link.sangoma.practice.category.Category

import com.kgoro.sangoma_link.user.enums.ServiceType
import java.math.BigDecimal

class PracticeRequest (
    val sangoma: Profile,
    val category: Category? = null,
    val name: String,
    val description: String? = null,
    val durationMinutes: Long,
    val price: BigDecimal,
    val currency: String = "ZAR",
    var serviceType: ServiceType
)