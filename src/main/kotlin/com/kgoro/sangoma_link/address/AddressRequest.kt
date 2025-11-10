package com.kgoro.sangoma_link.address

import org.locationtech.jts.geom.Point


class AddressRequest (
    val addressLine1: String?,
    val addressLine2: String?,
    val city: String?,
    val province: String?,
    val postalCode: String?,
    val country: String?,
    val latitude: Double,
    val longitude: Double,
    val isPrimary: Boolean,
    val isDefault: Boolean,
)