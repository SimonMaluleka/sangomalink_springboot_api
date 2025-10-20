package com.kgoro.sangoma_link.address

import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository:  JpaRepository<Address, Int> {
}