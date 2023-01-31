package com.v10.ratatouille23.controller

import com.v10.ratatouille23.dto.RestaurantDto
import com.v10.ratatouille23.mapper.RestaurantMapper
import com.v10.ratatouille23.service.RestaurantService
import org.springframework.http.ResponseEntity
import com.v10.ratatouille23.dto.ServerResponse
import com.v10.ratatouille23.utils.UserRoles
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/restaurant")
class RestaurantController (
    private val restaurantService: RestaurantService,
    private val restaurantMapper: RestaurantMapper
){

    //Query su tutti gli utenti attivi che hanno lo stesso ruolo nello stesso ristorante
    @GetMapping("/users/{role}")
    @PreAuthorize("hasAuthority('ADMIN')")
    fun getUsersByRole(@PathVariable("role") role: UserRoles) =
        ResponseEntity.ok(ServerResponse.ok(this.restaurantService.getUsersByRole(role,true)))

    @PostMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    fun add(@RequestBody restaurantDto: RestaurantDto) =
        ResponseEntity.ok(ServerResponse.ok(this.restaurantMapper.toDomain(restaurantService.add(restaurantDto))))


    @DeleteMapping()
    @PreAuthorize("hasAuthority('ADMIN')")
    fun delete() =
        ResponseEntity.ok(ServerResponse.ok(this.restaurantMapper.toDomain(restaurantService.delete())))

}