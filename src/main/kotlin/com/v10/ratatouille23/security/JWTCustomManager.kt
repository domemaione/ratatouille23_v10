package com.v10.ratatouille23.security

import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.util.*
import java.util.logging.Logger

@Component
class JWTCustomManager(
    @Value("\${spring.jwt.secret}")
    private val jwtSecret: String,

    @Value("\${spring.jwt.expiration}")
    private val jwtExpiration: Long
) {
    private val logger = Logger.getLogger(JWTCustomManager::class.java.name)

    fun validate(token: String): Boolean {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token)
            return true
        } catch (e: SignatureException) {
            logger.severe("Invalid JWT signature: ${e.message}")
        } catch (e: MalformedJwtException) {
            logger.severe("Invalid JWT token: ${e.message}")
        } catch (e: ExpiredJwtException) {
            logger.severe("JWT token is expired: ${e.message}")
        } catch (e: UnsupportedJwtException) {
            logger.severe("JWT token is unsupported: ${e.message}")
        } catch (e: IllegalArgumentException) {
            logger.severe("JWT claims string is empty: ${e.message}")
        }

        return false
    }

    fun generate(authentication: Authentication): String {
        val user = authentication.principal as CustomUserDetails
        val exp = Date(Date().time + this.jwtExpiration)

        return Jwts.builder()
            .setIssuer("Ratatouille23-Backend")
            .setSubject("JWT Auth Provider")
            .claim("email", user.username)
            .claim("role", user.getUser().role)
            .setIssuedAt(Date())
            .setExpiration(exp)
            .signWith(SignatureAlgorithm.HS512, jwtSecret)
            .compact()
    }

    fun getSubject(token: String): String {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body.subject
    }

    fun getEmail(token: String) =
        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).body["email"] as String
}