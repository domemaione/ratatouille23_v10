package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.ServerResponse
import com.v10.ratatouille23.mapper.UserMapper
import com.v10.ratatouille23.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController (
    private val userService: UserService,
    private val userMapper: UserMapper
){

    @GetMapping("{email}")
    fun get(@PathVariable("email") email: String) =
        ResponseEntity.ok(ServerResponse.ok(this.userMapper.toDomain(userService.get(email))))

    @GetMapping("all/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun  getAll(@PathVariable("id") restairantId: Long) =
        ResponseEntity.ok(ServerResponse.ok(userService.getAll(restairantId)))


//TODO() CANCELLARE L'UTENTE

}
