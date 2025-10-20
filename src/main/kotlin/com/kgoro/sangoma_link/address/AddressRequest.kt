package com.kgoro.sangoma_link.address

class AddressRequest (
    val userId: Long,
    val addressLine1: String,
    val addressLine2: String,
    val city: String,
    val province: String,
    val postalCode: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val isPrimary: Boolean,
    val isDefault: Boolean,
)