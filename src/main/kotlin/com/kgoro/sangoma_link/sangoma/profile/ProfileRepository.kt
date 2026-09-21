package com.kgoro.sangoma_link.sangoma.profile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SangomaProfileRepository : JpaRepository<Profile, Long> {

    // Finds a profile by navigating the OneToOne 'user' relationship to its ID
    fun findByUserId(userId: Long): Profile?

    // Finds all profiles where is_featured is true
    fun findByIsFeaturedTrue(): List<Profile>
}
