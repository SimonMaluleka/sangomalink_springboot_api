package com.kgoro.sangoma_link.admin

import com.kgoro.sangoma_link.sangoma.profile.Profile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AdminRepository: JpaRepository<Profile, Long> {
    @Query("""
      UPDATE sangoma_profiles p
      SET approval_status = 1
      WHERE p.user_id = :userId
      """, nativeQuery = true)
    fun approveSangoma(@Param("userId") userId: Long): Int

    @Query("""
      UPDATE sangoma_profiles p
      SET approval_status = 0
      WHERE p.user_id = :userId
      """, nativeQuery = true)
    fun rejectSangoma(@Param("userId") userId: Long): Int

    @Query("""
      SELECT * FROM sangoma_profiles 
      WHERE approval_status = :approvalStatus
      ORDER BY created_at DESC
      """, nativeQuery = true)
    fun getPendingProfiles(@Param("approvalStatus") approvalStatus: Int): List<Profile>
}