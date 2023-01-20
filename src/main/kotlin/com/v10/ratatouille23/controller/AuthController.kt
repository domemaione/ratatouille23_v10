package com.v10.ratatouille23.controller

import com.v10.ratatouille23.service.AuthService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.function.ServerResponse

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
){
    /*
    //Registrazione
    @PostMapping("signup")
    fun signup(@RequestBody signupRequestDto: SignupRequestDto) =
        ResponseEntity.ok(ServerResponse.ok(authService.signup(signupRequestDto))) */

}