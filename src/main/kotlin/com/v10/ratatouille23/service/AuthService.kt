package com.v10.ratatouille23.service

import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class AuthService(

) {
    private val logger = Logger.getLogger(AuthService::class.java.name)
  /*
    fun signup(user: SignupRequestDto): Boolean {
        val toSave = UserDto(null, user.email, user.name, user.surname, user.password, UserRoles.ADMIN, null, enabled = false)
        val saved = this.userService.save(toSave)
        val token = this.activationTokenManager.generate(saved.id.toString())
        this.emailService.send(saved, EmailService.MailComposer.Registration(token, "http://localhost:8080/api/auth/validate/user"))
        return true
    }

*/

}