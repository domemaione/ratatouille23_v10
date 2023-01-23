package com.v10.ratatouille23.service

import com.v10.ratatouille23.dto.UserDto
import com.v10.ratatouille23.model.User
import com.v10.ratatouille23.repository.UserRepository
import com.v10.ratatouille23.security.CustomUserDetails
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger

@Service
class UserService(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder
): UserDetailsService {
    private val logger = Logger.getLogger(UserService::class.java.name)

    //Restituisce l'user con parametro in input l'email
    fun get(email: String): User {
        this.logger.info("get() - incoming request with email: $email")
        val res: UserDto
        val found = this.userRepository.getByEmail(email)
        if (found.isEmpty)
            throw ResponseStatusException(HttpStatus.NO_CONTENT, "User not found!")
        this.logger.info("get() - returned obj: ${found.get()}")
        return found.get()
    }



    //Viene usata nel package security => JWTAuthorizationFilter, per prendere l'utente e verificare se è autorizzato
    override fun loadUserByUsername(username: String?): UserDetails {
        val email = username ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST)
        val user: User
        try {
            user = this.get(email)
        } catch (e: ResponseStatusException) {
            this.logger.warning("loadUserByUsername() - user: $email, status: ${e.status}")
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED)
        }
        return CustomUserDetails.build(user)
    }
}