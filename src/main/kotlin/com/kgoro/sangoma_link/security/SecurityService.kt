package com.kgoro.sangoma_link.security

import com.kgoro.sangoma_link.user.UserRepository
import com.kgoro.sangoma_link.user.enums.UserType
import org.springframework.core.MethodParameter
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

// Security Service for method-level security
@Service
class SecurityService(
    private val userRepository: UserRepository
) {
    fun isCurrentUser(userId: Long): Boolean {
        val authentication = SecurityContextHolder.getContext().authentication
        if (authentication?.principal is UserPrincipal) {
            val currentUserId = (authentication.principal as UserPrincipal).id
            return currentUserId == userId
        }
        return false
    }
}

// Custom annotation for accessing current user
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class CurrentUser

// Resolver for @CurrentUser annotation
@Component
class CurrentUserResolver : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(CurrentUser::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {
        val authentication = SecurityContextHolder.getContext().authentication
        print("Auth : $authentication")
        return authentication?.principal as? UserPrincipal
            ?: throw AccessDeniedException("User not authenticated")
    }
}

// UserPrincipal class
data class UserPrincipal(
    val id: Long,
    val email: String,
    val userType: UserType,
) : UserDetails {
    override fun getUsername(): String = email
    override fun getPassword(): String? = null
    override fun isEnabled(): Boolean = true
    override fun isAccountNonExpired(): Boolean = true
    override fun isAccountNonLocked(): Boolean = true
    override fun isCredentialsNonExpired(): Boolean = true
    override fun getAuthorities(): Collection<GrantedAuthority> = authorities
}