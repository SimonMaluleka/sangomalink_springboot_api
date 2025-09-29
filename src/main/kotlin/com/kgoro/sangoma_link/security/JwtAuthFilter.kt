package com.kgoro.sangoma_link.security

import com.kgoro.sangoma_link.user.CustomUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthFilter(
    val jwtService: JwtService,
    val userDetailsService: CustomUserDetailsService
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader(HttpHeaders.AUTHORIZATION)


        if (request.servletPath.contains("/api/v1/auth")){
            filterChain.doFilter(request, response)
            return
        }

        if(authHeader.doesNotContainBearerToken()){
            filterChain.doFilter(request, response)
            return
        }

        val jwtToken: String = authHeader!!.substringAfter("Bearer ")

        val userEmail = jwtService.extractEmail(jwtToken)



        if(userEmail != null && SecurityContextHolder.getContext().authentication == null){
            val foundUser = userDetailsService.loadUserByUsername(userEmail)

            if (jwtService.isTokenValid(jwtToken, foundUser)){
                updateSecurityContext(foundUser, request)
            }
            filterChain.doFilter(request,response)
        }
    }

    private fun updateSecurityContext(
        foundUser: UserDetails,
        request: HttpServletRequest
    ) {
        val authToken = UsernamePasswordAuthenticationToken(
            foundUser,
            null,
            foundUser.authorities
        )

        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)

        SecurityContextHolder.getContext().authentication = authToken

        print("Security Context: ${SecurityContextHolder.getContext().authentication}")
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.servletPath

        return path.startsWith("/auth/")
    }

}

private fun String?.doesNotContainBearerToken(): Boolean =
    this == null || !this.startsWith("Bearer")