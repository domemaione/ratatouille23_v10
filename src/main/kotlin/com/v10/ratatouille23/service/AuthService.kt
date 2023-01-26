package com.v10.ratatouille23.service

import com.v10.ratatouille23.component.ActivationTokenManager
import com.v10.ratatouille23.component.AuthenticatedUserHelper
import com.v10.ratatouille23.dto.UserDto
import com.v10.ratatouille23.dto.request.SignupRequestDto
import com.v10.ratatouille23.repository.UserRepository
import com.v10.ratatouille23.utils.UserRoles
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.logging.Logger

@Service
class AuthService(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val emailService: EmailService,
    private val activationTokenManager: ActivationTokenManager

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
        val toSave = UserDto(null, user.name, user.surname, user.email, user.password,role, restaurantId, enabled = false)
        val saved = this.userService.save(toSave)
        val token = this.activationTokenManager.generate(saved.id.toString())
        this.emailService.send(saved, EmailService.MailComposer.Registration(token, "http://localhost:8080/api/auth/validate/user"))
        return true
    }

    fun signupSubAccount(user: SignupRequestDto, token: String): Boolean {
        if(validate(token)) //se il token è valido
        {
            //Inserimento della nuova password diversa da quella inserita inizialmente
            //Rispettare le condizioni della passw ||TODO condizioni specifiche per la password
           //salvo i dati aggiornati nel db con attivazione dell'utente
            //email di notifica
            val role = user.role ?: throw IllegalStateException("Role cannot be null")
            val toSave = UserDto(null, user.name, user.surname, user.email, user.password,role, null, enabled = false)
            val saved = this.userService.save(toSave)
            val token = this.activationTokenManager.generate(saved.id.toString())
            this.emailService.send(saved, EmailService.MailComposer.Registration(token, "http://localhost:8080/api/auth/validate/user"))
        }

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

}