package com.kgoro.sangoma_link.common.address

data class HealerAddressesSearchRequest(
    val latitude: Double,
    val longitude: Double,
    val radiusKm: Double,
)
