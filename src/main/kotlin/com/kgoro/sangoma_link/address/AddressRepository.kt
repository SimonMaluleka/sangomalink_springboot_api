package com.kgoro.sangoma_link.address

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AddressRepository:  JpaRepository<Address, Long> {
    @Query("""
        SELECT * FROM user_addresses WHERE ST_DWithin(
        location,
        ST_SetSRID(ST_MakePoint(:lng,:lat), 4326)::geography,
        :radiusMeters
        ) ORDER BY ST_Distance(
        location,
        ST_SetSRID(ST_MakePoint(:lng,:lat), 4326)::geography
        )
    """, nativeQuery = true)
    fun findHealerAddressesNearby(
        @Param("lat") lat: Double,
        @Param("lng") lng: Double,
        @Param("radiusMeters") radiusMeters: Double
        ): List<Address>

    @Query("""
        SELECT * FROM user_addresses WHERE user_id = :userId;
    """, nativeQuery = true)
    fun findAllUserAddresses(
        @Param("userId") userId: Long
    ): List<Address>
}