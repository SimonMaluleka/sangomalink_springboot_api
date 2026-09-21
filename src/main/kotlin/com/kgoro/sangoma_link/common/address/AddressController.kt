package com.kgoro.sangoma_link.common.address

import com.kgoro.sangoma_link.user.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/address")
class AddressController (
    val addressService: AddressService,
    val userService: UserService
){
    @PostMapping("/create")
    @PreAuthorize("isAuthenticated()")
    fun addNewAddress(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody addressRequest: AddressRequest
    ){
        print(addressRequest.latitude)
        val user = userService.getUserByEmail(userDetails.username)
        print(user.userType)
        addressService.createAddress(
                user.id!!,
                addressRequest
                )
    }




    @GetMapping("/search")
    fun getHealerAddressesNearby(
        @RequestBody request: HealerAddressesSearchRequest
    ): ResponseEntity<List<AddressSearchResponse>> {
        val response = addressService.searchHealerAddresses(request)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/user")
    @PreAuthorize("isAuthenticated()")
    fun getAllAddresses(
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<List<UserAddressesResponse>> {
        val user = userService.getUserByEmail(userDetails.username)

        return ResponseEntity.ok(addressService.getAllUserAddresses(user.id!!))
    }
}