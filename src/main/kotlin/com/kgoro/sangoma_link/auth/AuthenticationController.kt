package com.kgoro.sangoma_link.auth
//import io.swagger.v3.oas.annotations.tags.Tag
import com.kgoro.sangoma_link.security.JwtTokenResponse
import com.kgoro.sangoma_link.security.TokenPair
import com.kgoro.sangoma_link.user.CreateUserRequest
import com.kgoro.sangoma_link.user.CustomUserDetailsService
import com.kgoro.sangoma_link.user.toSangomaSpecificData
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/auth")
//@Tag(name = "Authentication")
@CrossOrigin(origins = ["http://localhost:8000"])
class AuthenticationController(
    val authenticationService: AuthenticationService,
    val userDetailsService: CustomUserDetailsService
) {
    @PostMapping("/authenticate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun authenticate(@RequestBody @Valid request: AuthenticateRequest): ResponseEntity<AuthenticationResponse>{
        println("req:  $request")

        val response = authenticationService.authenticate(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun createUser(@RequestBody @Valid request: CreateUserRequest): ResponseEntity<Any>{
        authenticationService.createUser(
            email = request.email,
            password = request.passwordHash,
            firstName = request.firstName,
            lastName = request.lastName,
            userType = request.userType,
            phoneNumber = request.phoneNumber,
            profileImageUrl = request.profileImageUrl,
            sangomaSpecificData = request.toSangomaSpecificData()
        )
        return ResponseEntity.accepted().build()
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.ACCEPTED)
    fun refreshToken(@RequestBody @Valid request: RefreshTokenRequest): ResponseEntity<JwtTokenResponse> {
        val tokenResponse = authenticationService.refreshAccessToken(request.token)
            print("TokenResponse $tokenResponse")

//            tokenResponse?.mapToJwtTokenResponse()
//            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token!")

        return ResponseEntity.ok(tokenResponse?.mapToJwtTokenResponse()
            ?: throw ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid refresh token!"))
    }
}

fun TokenPair.mapToJwtTokenResponse(): JwtTokenResponse {
    return JwtTokenResponse(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken,
        expiresIn = this.expiresIn
    )
}



