package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.ActivationTokenManager
import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.component.PasswordValidator
import com.v10.ratatouille23.controller.exceptions.ExceptionController
import com.v10.ratatouille23.controller.exceptions.InvalidEmailException
import com.v10.ratatouille23.controller.exceptions.InvalidPasswordException
import com.v10.ratatouille23.dto.UserDto
import com.v10.ratatouille23.dto.request.ResetPasswordDto
import com.v10.ratatouille23.dto.request.SignupRequestDto
import com.v10.ratatouille23.mapper.UserMapper
import com.v10.ratatouille23.repository.UserRepository
import com.v10.ratatouille23.security.JWTCustomManager
import com.v10.ratatouille23.utils.UserRoles
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.validation.BeanPropertyBindingResult
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger

@Service
    class AuthService(
        @Value("\${spring.activation-token.url}")
        private val authUrl: String,

        private val userService: UserService,
        private val userRepository: UserRepository,
        private val userMapper: UserMapper,
        private val emailService: EmailService,
        private val activationTokenManager: ActivationTokenManager,
        private val jwtCustomManager: JWTCustomManager,
        private val passwordEncoder: BCryptPasswordEncoder,
        private val passwordValidator: PasswordValidator,

    ) {


        private val logger = Logger.getLogger(AuthService::class.java.name)

        //Registrazione utente con ruolo ADMIN
        fun signup(user: SignupRequestDto): Boolean {
            if (!EmailValidator.getInstance().isValid(user.email))
                throw InvalidEmailException("email not valid")
            //TODO non so se va bene, ma nel caso devo contrallare le policy anche in signup op
            val errors = passwordValidator.validate(user.password)
            if (errors.hasErrors())
                throw InvalidPasswordException("Password failed: $errors.message")

            val toSave = UserDto(null, user.name, user.surname, user.email, user.password, UserRoles.ADMIN, null, enabled = false, firstAccess = true)
            val saved = this.userService.save(toSave)
            val token = this.activationTokenManager.generate(saved.id.toString())
            this.emailService.send(saved, EmailService.MailComposer.Registration(token,
                "$authUrl/api/auth/validate/user"
            ))
            return true
        }

        //Registrazione operatore
        fun signupOp(user: SignupRequestDto): Boolean {
            val admin = AuthenticatedUserHelper.get()
            val restaurantId = admin?.restaurantId ?: throw IllegalStateException("Restaurant is not exists")
            val role = user.role ?: throw IllegalStateException("Role cannot be null")
            val toSave = UserDto(null, user.name, user.surname, user.email,user.password,role, restaurantId, enabled = false, firstAccess = false)
            val saved = this.userService.save(toSave)
            val token = this.activationTokenManager.generate(saved.id.toString())
            this.emailService.send(saved, EmailService.MailComposer.Registration(token, "$authUrl/api/auth/validate/user"))
           // this.emailService.send(saved, EmailService.MailComposer.Registration(token, "http://localhost:8080/api/auth/signup/op/resetpassword"))
            return true
        }

    //reset password per un nuovo utente creato dall'admin
    fun resetPassword(newpassord: ResetPasswordDto): Boolean{
        val user = AuthenticatedUserHelper.get() ?: throw IllegalStateException("User not found")

        if(passwordEncoder.matches(newpassord.password,user.password))
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "password same as previous one")

        user.password = passwordEncoder.encode(newpassord.password)
        user.firstAccess = true
        val saved = this.userRepository.save(user) //bisogna fare userService
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


/*fun resetPassword(newpassord: ResetPasswordDto, token: String): Boolean{
        if(!validate(token))
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token")

        val id = this.activationTokenManager.validate(token) ?: throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR)
        val found = this.userService.get(id)
        if(passwordEncoder.matches(newpassord.password,found.password))
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "password same as previous one")



        found.password = passwordEncoder.encode(newpassord.password)
        found.firstAccess = true
        found.enabled = true
        val saved = this.userRepository.save(found) //bisogna fare userService

        this.emailService.send(saved, EmailService.MailComposer.ResetPassword)

        return true

    }*/