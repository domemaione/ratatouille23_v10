package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.ActivationTokenManager
import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.dto.UserDto
import com.v10.ratatouille23.dto.request.ResetPasswordDto
import com.v10.ratatouille23.dto.request.SignupRequestDto
import com.v10.ratatouille23.mapper.UserMapper
import com.v10.ratatouille23.repository.UserRepository
import com.v10.ratatouille23.security.JWTCustomManager
import com.v10.ratatouille23.utils.UserRoles
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger

    @Service
    class AuthService(
        private val userService: UserService,
        private val userRepository: UserRepository,
        private val userMapper: UserMapper,
        private val emailService: EmailService,
        private val activationTokenManager: ActivationTokenManager,
        private val jwtCustomManager: JWTCustomManager,
        private val passwordEncoder: BCryptPasswordEncoder
    ) {
        private val logger = Logger.getLogger(AuthService::class.java.name)

        //Registrazione utente con ruolo ADMIN
        fun signup(user: SignupRequestDto): Boolean {
            val toSave = UserDto(null, user.name, user.surname, user.email, user.password, UserRoles.ADMIN, null, enabled = false)
            val saved = this.userService.save(toSave)
            val token = this.activationTokenManager.generate(saved.id.toString())
            this.emailService.send(saved, EmailService.MailComposer.Registration(token, "http://localhost:8080/api/auth/validate/user"))
            return true
        }

        //Registrazione operatore
        fun signupOp(user: SignupRequestDto): Boolean {
            val admin = AuthenticatedUserHelper.get()
            val restaurantId = admin?.restaurantId ?: throw IllegalStateException("Restaurant is not exists")
            val role = user.role ?: throw IllegalStateException("Role cannot be null")
            val toSave = UserDto(null, user.name, user.surname, user.email,user.password,role, restaurantId, enabled = false)
            val saved = this.userService.save(toSave)
            val token = this.activationTokenManager.generate(saved.id.toString())
           // this.emailService.send(saved, EmailService.MailComposer.Registration(token, "http://localhost:8080/api/auth/validate/user"))
            this.emailService.send(saved, EmailService.MailComposer.Registration(token, "http://localhost:8080/api/auth/signup/op/resetpassword"))

            return true
        }

    //reset password per un nuovo utente creato dall'admin
    fun resetPassword(newpassord: ResetPasswordDto, token: String): Boolean{
        if(!validate(token))
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token")

        val id = this.activationTokenManager.validate(token) ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        val found = this.userService.get(id)
        if(passwordEncoder.matches(newpassord.password,found.password))
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "password same as previous one")


        found.enabled = true
        found.password = passwordEncoder.encode(newpassord.password)
        val saved = this.userRepository.save(found) //bisogna fare userService

        this.emailService.send(saved, EmailService.MailComposer.ResetPassword)

        return true

    }



        //verifica che il token sia valido e attiva l'utente appena registrato
        fun validate(token: String): Boolean {
            val id = this.activationTokenManager.validate(token) ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
            val found = this.userService.get(id)
            found.enabled = true //per attivare l'utente
            val saved = this.userRepository.save(found)
            return true
        }


        fun checkToken(token: String): Boolean {
            this.logger.info("checkToken() - incoming request with token: $token")
            if (!this.jwtCustomManager.validate(token))
                throw ResponseStatusException(HttpStatus.UNAUTHORIZED)

            return true
        }

    }