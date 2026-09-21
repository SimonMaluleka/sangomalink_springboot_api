package com.kgoro.sangoma_link.common.address

import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.locationtech.jts.geom.Point
import org.locationtech.jts.geom.PrecisionModel
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AddressService(
    val addressRepository: AddressRepository,
) {

    private val geometryFactory = GeometryFactory(PrecisionModel(), 4326)

    fun getAllUserAddresses(userId: Long): List<UserAddressesResponse> {
        val addresses = addressRepository.findAllUserAddresses(userId)

        val userAddressList = addresses.map {
            address ->
            UserAddressesResponse(
                addressLine1 = address.addressLine1,
                addressLine2 = address.addressLine2,
                city = address.city,
                province = address.province,
                postalCode = address.postalCode,
                country = address.country,
                isDefault = address.isDefault,
                isPrimary = address.isPrimary
            )
        }.toList()
        return userAddressList
    }

    fun getAllUserAddressById(id: Long) = addressRepository.findById(id)


    fun createAddress(userId: Long, addressRequest: AddressRequest) {
        print("\n user: $userId address: ${addressRequest.addressLine1}")
        // Create the JTS Point object using longitude, then latitude
        val locationPoint: Point = geometryFactory.createPoint(
            Coordinate(addressRequest.longitude, addressRequest.latitude)
        )
        // Ensure the SRID is set correctly for PostGIS to interpret it as geography(Point, 4326)
        locationPoint.srid = 4326

        // Update the healer's location (assuming a 1-to-1 relationship, or you modify your schema)
        try {
            val newAddress = Address(
                id = 0,
                userId = userId,
                addressLine1 = addressRequest.addressLine1!!,
                addressLine2 = addressRequest.addressLine2!!,
                city = addressRequest.city!!,
                province = addressRequest.province!!,
                postalCode = addressRequest.postalCode!!,
                country = addressRequest.country!!,
                location = locationPoint,
                isPrimary = addressRequest.isPrimary,
                isDefault = addressRequest.isDefault,
                createdAt = LocalDateTime.now(),
                updatedAt = LocalDateTime.now()
            )
            print("\n new address"+ newAddress.addressLine1)

            addressRepository.save<Address>(newAddress)
        } catch (e: Exception) {
            print("message: " + e.cause)
        }
    }
    fun searchHealerAddresses(request: HealerAddressesSearchRequest): List<AddressSearchResponse> {
        val radiusMeters = request.radiusKm * 1000

        print("\n" + radiusMeters.toString() +" " + request.longitude +" "+ request.latitude)

        val foundAddresses = addressRepository.findHealerAddressesNearby(
            request.latitude,
            request.longitude,
            radiusMeters
        ).map {
            address ->
            AddressSearchResponse(
                addressLine1 = address.addressLine1,
                addressLine2 = address.addressLine2,
                city = address.city,
                province = address.province,
                postalCode = address.postalCode,
                country = address.country
            )
        }.toList()

        println("Found: $foundAddresses")
        return foundAddresses
    }
}