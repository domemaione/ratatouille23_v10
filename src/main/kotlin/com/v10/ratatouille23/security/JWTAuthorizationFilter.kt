package com.v10.ratatouille23.security

import com.v10.ratatouille23.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.server.ResponseStatusException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JWTAuthorizationFilter(
    authManager: AuthenticationManager,
    private val jwtCustomManager: JWTCustomManager,
    private val userService: UserService
): BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header: String? = request.getHeader("Authorization")

        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        val token = request.getHeader("Authorization").replace("Bearer ", "")

        if(!this.jwtCustomManager.validate(token))
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)

        val email = this.jwtCustomManager.getEmail(token)
        val user = this.userService.loadUserByUsername(email)
        val auth = UsernamePasswordAuthenticationToken(user, null, user.authorities)

        SecurityContextHolder.getContext().authentication = auth;
        chain.doFilter(request, response);
    }
}