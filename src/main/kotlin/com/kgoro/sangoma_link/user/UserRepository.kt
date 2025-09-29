package com.kgoro.sangoma_link.user

import com.kgoro.sangoma_link.user.enums.UserType
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

    fun findByUserType(userType: UserType): List<User>

    fun findByIsActiveTrue(): List<User>

    fun findByIsVerifiedTrue(): List<User>

    fun findByUserTypeAndIsActiveTrue(userType: UserType): List<User>

    @Query("""
        SELECT u FROM User u 
        WHERE u.userType = 'SANGOMA' 
        AND u.isActive = true
        AND (LOWER(u.firstName) LIKE LOWER(CONCAT('%', :query, '%')) 
             OR LOWER(u.lastName) LIKE LOWER(CONCAT('%', :query, '%'))
             OR LOWER(u.email) LIKE LOWER(CONCAT('%', :query, '%')))
    """)
    fun findActiveSangomasBySearchQuery(
        @Param("query") query: String,

    ): List<User>

    @Query("SELECT u FROM User u WHERE u.isActive = true AND u.userType = 'SANGOMA'")
    fun findActiveSangomas(): List<User>

    @Query("SELECT u FROM User u WHERE u.email = :email AND u.isActive = true")
    fun findActiveUserByEmail(@Param("email") email: String): User?

    @Query("SELECT COUNT(u) FROM User u WHERE u.userType = :userType AND u.isActive = true")
    fun countActiveUsersByType(@Param("userType") userType: UserType): Long

//    @Query("SELECT u FROM User u JOIN u.sangomaProfile sp WHERE sp.approvalStatus = 'APPROVED' AND u.isActive = true")
//    fun findApprovedSangomas(): List<User>

    fun findByIdAndIsActiveTrue(id: Long): Optional<User>
}