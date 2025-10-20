package com.kgoro.sangoma_link.auth

import com.kgoro.sangoma_link.access_token.AccessToken
import com.kgoro.sangoma_link.access_token.AccessTokenRepository
import com.kgoro.sangoma_link.config.JwtProperties
import com.kgoro.sangoma_link.exception.EmailAlreadyExistsException
import com.kgoro.sangoma_link.mail.EmailService
import com.kgoro.sangoma_link.mail.EmailTemplate
import com.kgoro.sangoma_link.sangoma_profile.SangomaProfileService
import com.kgoro.sangoma_link.sangoma_profile.SangomaSpecificData
import com.kgoro.sangoma_link.security.JwtService
import com.kgoro.sangoma_link.security.TokenPair
import com.kgoro.sangoma_link.user.*
import com.kgoro.sangoma_link.user.enums.UserType
import org.apache.tomcat.websocket.AuthenticationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextImpl
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.security.SecureRandom
import java.time.LocalDateTime
import java.util.*
import kotlin.String

@Service
class AuthenticationService(
    val authenticationManager: AuthenticationManager,
    val userDetailsService: CustomUserDetailsService,
    val jwtService: JwtService,
    val jwtProperties: JwtProperties,
    val passwordEncoder: PasswordEncoder,
    val userRepository: UserRepository,
    val tokenRepository: TokenRepository,
    val accessTokenRepository: AccessTokenRepository,
    val sangomaProfileService: SangomaProfileService,
    val emailService: EmailService,
    @param:Value("\${application.mailing.frontend.activation-url}")
    val activationUrl: String,

    ) {
    fun createUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        userType: UserType,
        phoneNumber: String? = null,
        profileImageUrl: String? = null,
        sangomaSpecificData: SangomaSpecificData? = null
    ): User {
        // Validate email uniqueness
        if (userRepository.existsByEmail(email)) {
            throw EmailAlreadyExistsException("Email already registered: $email")
        }

        // Create user entity
        val user = when (userType) {
            UserType.Sangoma -> User.createSangoma(
                email = email,
                passwordHash = passwordEncoder.encode(password),
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                profileImageUrl = profileImageUrl
            )
            UserType.Customer -> User.createCustomer(
                email = email,
                passwordHash = passwordEncoder.encode(password),
                firstName = firstName,
                lastName = lastName,
                phoneNumber = phoneNumber,
                profileImageUrl = profileImageUrl
            )

            else -> {
                User.createAdmin(
                    email = email,
                    passwordHash = passwordEncoder.encode(password),
                    firstName = firstName,
                    lastName = lastName,
                    phoneNumber = phoneNumber,
                    profileImageUrl = profileImageUrl
                )
            }
        }

        val savedUser = userRepository.save(user)

        // Create sangoma profile if user type is sangoma
        if (userType == UserType.Sangoma && sangomaSpecificData != null) {
            sangomaProfileService.createSangomaProfile(savedUser, sangomaSpecificData)
        }

        return savedUser
    }

    private fun sendValidationEmail(user: User) {
        val newToken = generateAndSaveActivationToken(user)
        // send email
        emailService.sendEmail(
            user.email,
            user.firstName + " " + user.lastName ,
            EmailTemplate.ACTIVATE_ACCOUNT,
            activationUrl,
            newToken,
            "Account activation"
        )
    }

    private fun generateAndSaveActivationToken(user: User): String {
        val generatedToken: String = generateActivationCode(6)
        val token = Token.Builder()
            .setToken(generatedToken)
            .setCreatedAt(LocalDateTime.now())
            .setExpiresAt(LocalDateTime.now().plusMinutes(15))
            .setValidated(LocalDateTime.now())
            .setUser(user)
            .build()
        tokenRepository.save(token)
        return generatedToken
    }

    private fun generateActivationCode(length: Long): String {
        val characters = "0123456789"
        val codeBuilder: StringBuilder = StringBuilder()

        val secureRandom = SecureRandom()
        for (i in 0..<length) {
            val randomIndex: Int = secureRandom.nextInt(characters.length)
            codeBuilder.append(characters[randomIndex])
        }
        return codeBuilder.toString()
    }

    @Throws(AuthenticationException::class)
    fun authenticate(request: AuthenticateRequest): AuthenticationResponse {
            val authentication = authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request.email,
                    request.password
                )
            )

            print("\n Authentication " + authentication +"\n")
            val claims = HashMap<String?, Any>()

            val user = userDetailsService.loadUserByUsername(request.email)

            claims["fullName"] = user.username
            claims["roles"] = user.authorities

            val accessToken = generateAccessToken(user, claims)

            val refreshAccessToken = generateRefreshToken(user)

            val newAccessToken = AccessToken(
                0,
                accessToken,
                refreshAccessToken,
                user,
                false
            )

            accessTokenRepository.save(newAccessToken)

            return AuthenticationResponse(
                token = accessToken,
                refreshJwtToken = refreshAccessToken
            )
        }

    private fun generateRefreshToken(user: UserDetails): String = jwtService.generateToken(
        user,
        Date(System.currentTimeMillis() + jwtProperties.refreshTokenExpiration)
    )

    private fun generateAccessToken(user: UserDetails, extraClaims: Map<String?, Any?>,): String = jwtService.generateToken(
        userDetails = user,
        expirationDate = Date(System.currentTimeMillis() + jwtProperties.accessTokenExpiration),
        extraClaims = extraClaims
    )

    //    @Transactional
    //@Throws(MessagingException::class)
//    fun activateAccount(token: String) {
//        val savedToken = tokenRepository.findByToken(token)
//            .orElseThrow {
//                RuntimeException("Invalid token")
//            }
//
//        if(LocalDateTime.now().isAfter(savedToken.expiresAt)){
//            sendValidationEmail(savedToken.user)
//            throw RuntimeException("Activation token has expired. A new token has been sent to the same email address")
//        }
//
//        val user = userRepository.findByEmail(savedToken.user.email)
//
//        user?.isActive = true
//        user?.isVerified = true
//
//        userRepository.save(user)
//
//        savedToken.validated = LocalDateTime.now()
//
//        tokenRepository.save(savedToken)
//    }
//
    fun refreshAccessToken(token: String): TokenPair? {
        val extractedEmail = jwtService.extractEmail(token)
        println("Extracted email: $extractedEmail") // Or use logger

        return extractedEmail?.let { email ->
            print(email)
            val currentUserDetails = userDetailsService.loadUserByUsername(email)
            println("\n Current user details: $currentUserDetails")

            val refreshTokenUserDetails = accessTokenRepository.findUserDetailsByRefreshAccessToken(token)
            print("\nRefreshTokenUserDetails ${refreshTokenUserDetails}")
            if (!jwtService.isTokenExpired(token) &&
                currentUserDetails.username == refreshTokenUserDetails.userDetails.username) {

                val claims = mutableMapOf<String?, Any>(
                    "fullName" to currentUserDetails.username,
                    "roles" to currentUserDetails.authorities
                )

                val accessToken = generateAccessToken(currentUserDetails, claims)
                val refreshAccessToken = generateRefreshToken(currentUserDetails)

                val newAccessToken = AccessToken(
                    0,
                    accessToken,
                    refreshAccessToken,
                    currentUserDetails,
                    false
                )
                accessTokenRepository.save(newAccessToken)
                return TokenPair(
                    accessToken = accessToken,
                    refreshToken = refreshAccessToken,
                    expiresIn = 15*60L
                )
            } else {
                return null
            }
        }
    }

}



