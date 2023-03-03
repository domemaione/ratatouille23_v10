package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.ServerResponse
import com.v10.ratatouille23.model.DishAllergens
import com.v10.ratatouille23.service.DishAllergensService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/dishallergens")
class DishAllergensController(
    private val dishAllergensService: DishAllergensService
){
    @GetMapping("all")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERVISOR')")
    fun getAll() =
        ResponseEntity.ok(ServerResponse.ok(dishAllergensService.getAll()))
}