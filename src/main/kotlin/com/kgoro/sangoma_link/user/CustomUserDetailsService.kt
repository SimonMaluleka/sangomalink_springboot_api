package com.kgoro.sangoma_link.user

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

typealias ApplicationUser = com.kgoro.sangoma_link.user.User
@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val foundUser = userRepository.findByEmail(username)

            return foundUser?.mapToUserDetails()
            ?: throw UsernameNotFoundException("Not Found!")
    }


    private fun ApplicationUser.mapToUserDetails(): UserDetails {
        print(this.userType)

    return User.builder()
    .username(this.email)
    .password(this.passwordHash)
    .roles(this.userType.name)
    .build()
}
}



