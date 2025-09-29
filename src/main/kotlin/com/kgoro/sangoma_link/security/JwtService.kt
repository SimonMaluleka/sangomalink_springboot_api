package com.kgoro.sangoma_link.security

import com.kgoro.sangoma_link.config.JwtProperties
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*
import java.util.function.Function

@Service
class JwtService(
    private val jwtProperties: JwtProperties
) {

    private val secretKey = Keys.hmacShaKeyFor(
        jwtProperties.secretKey.toByteArray()
    )

    //    Generate Token
    fun generateToken(
        userDetails: UserDetails,
        expirationDate: Date,
        extraClaims: Map<String?, Any?> = emptyMap()
    ): String = Jwts.builder()
        .claims()
        .subject(userDetails.username)
        .issuedAt(Date(System.currentTimeMillis()))
        .expiration(expirationDate)
        .add(extraClaims)
        .and()
        .signWith(secretKey)
        .compact()

    //    Extract all claims
    private fun extractAllClaims(token: String?): Claims {
        val parser = Jwts.parser()
            .verifyWith(secretKey)
            .build()

        return parser
            .parseSignedClaims(token)
            .payload
    }

//Extract email
    fun extractEmail(token: String): String? = extractAllClaims(token)
        .subject
// check token not expired
fun isTokenExpired(token: String): Boolean = extractAllClaims(token)
        .expiration
        .before(Date(System.currentTimeMillis()))

// check token is valid
    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val email = extractEmail(token)
        return (email == userDetails.username) && !isTokenExpired(token)
    }

//    fun getExpirationDate(token: String): Date {
//        return Jwts.parserBuilder()
//            .setSigningKey(signingKey) // Replace with your actual signing key
//            .build()
//            .parseClaimsJws(token)
//            .body
//            .expiration
//    }
//
//    fun getExpiration(token: String): Long {
//        val expirationDate = getExpirationDate(token)
//        val now = Date()
//        val diffMillis = expirationDate.time - now.time
//        return diffMillis.coerceAtLeast(0) / 1000 // return in seconds
//    }


}
