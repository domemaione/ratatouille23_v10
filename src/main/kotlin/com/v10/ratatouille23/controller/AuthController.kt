package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.request.SignupRequestDto
import com.v10.ratatouille23.service.AuthService
import com.v10.ratatouille23.dto.ServerResponse
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
){

    //Registrazione per utete ADMIN
    @PostMapping("signup")
    fun signup(@RequestBody signupRequestDto: SignupRequestDto) =
        ResponseEntity.ok(ServerResponse.ok(authService.signup(signupRequestDto)))

    //Registrazione per utente OPERATORE
    @PostMapping("signup/op")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun signupOp(@RequestBody signupRequestDto: SignupRequestDto) =
        ResponseEntity.ok(ServerResponse.ok(authService.signupOp(signupRequestDto)))



    @PostMapping("signup/op/resetpassword/{token}")
    fun signupSubAccount(@RequestBody signupRequestDto: SignupRequestDto, @PathVariable("token") token: String) =
        ResponseEntity.ok(ServerResponse.ok(authService.signupSubAccount(signupRequestDto,token)))

    @GetMapping("validate/user/{token}")
    fun validate(@PathVariable("token") token: String) =
        ResponseEntity.ok(ServerResponse.ok(authService.validate(token)))



}