package com.v10.ratatouille23.service

import com.v10.ratatouille23.dto.UserDto
import com.v10.ratatouille23.dto.request.SignupRequestDto
import com.v10.ratatouille23.utils.UserRoles
import com.v10.ratatouille23.component.ActivationTokenManager
import com.v10.ratatouille23.repository.UserRepository
import com.v10.ratatouille23.security.JWTCustomManager
import org.springframework.http.HttpStatus

import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger

@Service
class AuthService(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val emailService: EmailService,
    private val activationTokenManager: ActivationTokenManager,
    private val jwtCustomManager: JWTCustomManager
) {
    private val logger = Logger.getLogger(AuthService::class.java.name)

    //Registrazione utente
    fun signup(user: SignupRequestDto): Boolean {
        val toSave = UserDto(null, user.name, user.surname, user.email, user.password, UserRoles.ADMIN, null, enabled = false)
        val saved = this.userService.save(toSave)
        val token = this.activationTokenManager.generate(saved.id.toString())
        this.emailService.send(saved, EmailService.MailComposer.Registration(token, "http://localhost:8080/api/auth/validate/user"))
        return true
    }

    //
    fun validate(token: String): Boolean {
        val id = this.activationTokenManager.validate(token) ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        val found = this.userService.get(id)
        found.enabled = true
        val saved = this.userRepository.save(found)
        return true
    }

}