package com.kgoro.sangoma_link.address

import com.kgoro.sangoma_link.user.User
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AddressService(
    val addressRepository: AddressRepository
) {
    fun getAllAddresses(): ResponseEntity<List<Address>> {
        val addresses = addressRepository.findAll()
        print("\nAll addresses $addresses")
        return ResponseEntity.ok(addresses)
    }

    fun getAllUserAddressById(id: Long) = addressRepository.findById(id)
    //fun addNewAddress(address: AddressRequest): Boolean = addressRepository.save<Address>(address.toAddressModel())
}