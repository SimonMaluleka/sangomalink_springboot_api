package com.kgoro.sangoma_link.address

import com.kgoro.sangoma_link.user.User
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AddressService(
    val addressRepository: AddressRepository
) {
    fun getAllAddresses(): ResponseEntity<List<Address>> {
        val addresses = addressRepository.findAll()
        print("\nAll addresses $addresses")
        return ResponseEntity.ok(addresses)
    }

    fun getAllUserAddressById(id: Int) = addressRepository.findById(id)

    fun addNewAddress(address: AddressRequest): Address = addressRepository.save(Address(
        id = 0,
        userId = address.userId,
        addressLine1 = address.addressLine1,
        addressLine2 = address.addressLine2,
        city = address.city,
        province = address.province,
        postalCode = address.postalCode,
        country = address.country,
        latitude = address.latitude,
        longitude = address.longitude,
        isPrimary = address.isPrimary,
        isDefault = address.isDefault,
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    ))
}