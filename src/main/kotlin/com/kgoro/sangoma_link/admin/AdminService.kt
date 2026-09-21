package com.kgoro.sangoma_link.admin

import com.kgoro.sangoma_link.sangoma.profile.Profile
import org.springframework.stereotype.Service

@Service
class AdminService (
    val adminRepository: AdminRepository
){
    fun getPendingProfiles(status: Int): List<Profile> {
        val profiles = adminRepository.getPendingProfiles(status)
        return profiles
    }

    fun approveSangoma(id: Long): Int {
        return adminRepository.approveSangoma(id)
//            .orElseThrow {
//                EntityNotFoundException("Sangoma with ID: $id not found!")
//            }
    }
    fun rejectSangoma(id: Long): Int {
        return adminRepository.rejectSangoma(id)

    }
}