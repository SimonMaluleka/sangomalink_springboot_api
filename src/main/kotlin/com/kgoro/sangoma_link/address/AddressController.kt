package com.kgoro.sangoma_link.address

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/address")
class AddressController (
    val addressService: AddressService
){
    @GetMapping
    fun getAllAddresses(): ResponseEntity<List<Address>> = addressService.getAllAddresses()

    @PostMapping("/address/create")
    fun addNewAddress(addressRequest: AddressRequest): Address = addressService.addNewAddress(
        addressRequest
    )
}