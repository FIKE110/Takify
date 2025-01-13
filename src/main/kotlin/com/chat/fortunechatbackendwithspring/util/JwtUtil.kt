package com.chat.fortunechatbackendwithspring.util

import com.chat.fortunechatbackendwithspring.model.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Key
import java.util.*

@Service
class JwtUtil {

    @Value("\${jwt.signature}")
    private var signature: String? = null

    @Value("\${jwt.expiresAt}")
    private var expiresAt: Long = 0

    fun generateToken(username: String): String? {
        return Jwts
            .builder()
            .setSubject(username)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + expiresAt))
            .signWith(SignatureAlgorithm.HS256,signature)
            .compact()
    }

    private fun getSigningKey(): ByteArray {
        return Base64.getDecoder().decode(signature)
    }

    fun validateToken(token: String): Boolean {
        return !isTokenExpired(token)
    }

    fun extractClaims(token: String): Claims {
        return Jwts
            .parser()
            .setSigningKey(signature)
            .parseClaimsJws(token)
            .body
    }

    fun extractUsername(token: String): String? {
        return extractClaims(token).subject
    }

    fun isTokenExpired(token: String): Boolean {
        return extractClaims(token).expiration.before(Date())
    }


}