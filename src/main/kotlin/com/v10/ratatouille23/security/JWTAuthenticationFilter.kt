package com.v10.ratatouille23.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.v10.ratatouille23.model.User
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthenticationFilter(
    authenticationManager: AuthenticationManager?,
    private val jwtCustomManager: JWTCustomManager
): UsernamePasswordAuthenticationFilter(authenticationManager) {
    init {
        setFilterProcessesUrl("/api/auth/login")

    }

    companion object {
        private val logger = Logger.getLogger(JWTAuthenticationFilter::class.java.name)
    }

    override fun attemptAuthentication(request: HttpServletRequest?, response: HttpServletResponse?): Authentication {
        try {
            val user = ObjectMapper().readValue(request?.inputStream, User::class.java)
            return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    user.email,
                    user.password,
                    listOf()
                )
            )
        } catch (e: Exception) {
            logger.error("attemptAuthentication() - error: ${e.message}")
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest?, response: HttpServletResponse?, chain: FilterChain?, authResult: Authentication?) {
        if(authResult == null)
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        val token = this.jwtCustomManager.generate(authResult)
        response?.writer?.write(token);
        response?.writer?.flush();
    }
}