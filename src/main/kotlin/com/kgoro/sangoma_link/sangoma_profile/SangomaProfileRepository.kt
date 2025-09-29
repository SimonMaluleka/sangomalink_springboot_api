package com.kgoro.sangoma_link.sangoma_profile

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SangomaProfileRepository: JpaRepository<SangomaProfile, Long>